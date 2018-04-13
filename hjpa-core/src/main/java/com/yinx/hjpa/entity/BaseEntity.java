package com.yinx.hjpa.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.yinx.hjpa.ioc.InstanceFactory;
import com.yinx.hjpa.utils.BeanUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.util.Assert;

import com.yinx.hjpa.querychannel.QueryChannelService;

/**
 * 提供实体类的增删改查操作
 */
@MappedSuperclass
@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class BaseEntity implements Entity {
	private static final long serialVersionUID = 8882145540383345037L;
	public static final String PARAMETERS_CANNOT_BE_NULL = "Parameters cannot be null";
	private static EntityRepository repository;
	private static QueryChannelService queryChannel;

	@Override
	public boolean existed() {
		Object id = getId();
		if (id == null) {
			return false;
		}
		if (((id instanceof Number)) && (((Number) id).intValue() == 0)) {
			return false;
		}
		return getRepository().exists(getClass(), getId());
	}

	public static QueryChannelService getQueryChannel() {
		if (queryChannel == null) {
			queryChannel = InstanceFactory.getInstance(QueryChannelService.class);
		}
		return queryChannel;
	}

	@Override
	public boolean notExisted() {
		return !existed();
	}

	public static EntityRepository getRepository() {
		if (repository == null) {
			repository = InstanceFactory.getInstance(EntityRepository.class);
		}
		return repository;
	}

	public static void setRepository(EntityRepository repository) {
		BaseEntity.repository = repository;
	}

	public String[] businessKeys() {
		return new String[0];
	}

	@Override
	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder(13, 37);
		Map propValues = new BeanUtils(this).getPropValues();

		for (String businessKey : businessKeys()) {
			builder = builder.append(propValues.get(businessKey));
		}
		return builder.toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (other == null) {
			return false;
		}
		if ((businessKeys() == null) || (businessKeys().length == 0)) {
			return false;
		}
		if (!getClass().isAssignableFrom(other.getClass())) {
			return false;
		}
		Map thisPropValues = new BeanUtils(this)
				.getPropValuesExclude(new Class[] { Transient.class });
		Map otherPropValues = new BeanUtils(other)
				.getPropValuesExclude(new Class[] { Transient.class });
		EqualsBuilder builder = new EqualsBuilder();
		for (String businessKey : businessKeys()) {
			builder.append(thisPropValues.get(businessKey), otherPropValues.get(businessKey));
		}
		return builder.isEquals();
	}

	/**
	 * 将实体本身持久化到数据库
	 */
	protected void save() {
		getRepository().save(this);
	}

	/**
	 * 将实体本身从数据库中删除
	 */
	protected void remove() {
		getRepository().remove(this);
	}

	/**
	 * 根据实体类型和ID从仓储中获取实体
	 *
	 * @param <T>
	 *            实体类型
	 * @param clazz
	 *            实体的类
	 * @param id
	 *            实体的ID
	 * @return 类型为T或T的子类型，ID为id的实体。
	 */
	public static <T extends Entity> T get(Class<T> clazz, Serializable id) {
		return getRepository().get(clazz, id);
	}

	/**
	 * 查找实体在数据库中的未修改版本
	 *
	 * @param <T>
	 *            实体类型
	 * @param clazz
	 *            实体的类
	 * @param entity
	 *            实体
	 * @return 实体的未修改版本。
	 */
	public static <T extends Entity> T getUnmodified(Class<T> clazz, T entity) {
		return getRepository().getUnmodified(clazz, entity);
	}

	/**
	 * 根据实体类型和ID从仓储中加载实体(与get()方法的区别在于除id外所有的属性值都未填充)
	 *
	 * @param <T>
	 *            实体类型
	 * @param clazz
	 *            实体的类
	 * @param id
	 *            实体的ID
	 * @return 类型为T或T的子类型，ID为id的实体。
	 */
	public static <T extends Entity> T load(Class<T> clazz, Serializable id) {
		return getRepository().load(clazz, id);
	}

	/**
	 * 查找指定类型的所有实体
	 *
	 * @param <T>
	 *            实体所属的类型
	 * @param clazz
	 *            实体所属的类
	 * @return 符合条件的实体列表
	 */
	public static <T extends Entity> List<T> findAll(Class<T> clazz) {
		return getRepository().createCriteriaQuery(clazz).list();
	}

	/**
	 * 根据单个属性值以“属性=属性值”的方式查找实体
	 *
	 * @param <T>
	 *            实体所属的类型
	 * @param clazz
	 *            实体所属的类
	 * @param propName
	 *            属性名
	 * @param value
	 *            匹配的属性值
	 * @return 符合条件的实体列表
	 */
	public static <T extends Entity> List<T> findByProperty(Class<T> clazz, String propName,
																			 Object value) {
		return getRepository().findByProperty(clazz, propName, value);
	}

	/**
	 * 根据单个属性值以“属性=属性值”的方式查找实体，查询返回list的第一条数据，如果list为空时返回null
	 *
	 * @param clazz
	 * @param propName
	 * @param value
	 * @return
	 * @author YixinCapital -- wujt 2016年9月12日 下午2:17:03
	 */
	public static <T extends Entity> T findFirstByProperty(Class<T> clazz, String propName,
																			Object value) {
		List<T> result = getRepository().findByProperty(clazz, propName, value);
		if (result != null && !result.isEmpty()) {
			return result.get(0);
		}
		return null;
	}

	/**
	 * 根据多个属性值以“属性=属性值”的方式查找实体，例如查找name="张三", age=35的员工。
	 *
	 * @param <T>
	 *            实体所属的类型
	 * @param clazz
	 *            实体所属的类
	 * @param propValues
	 *            属性值匹配条件
	 * @return 符合条件的实体列表
	 */
	public static <T extends Entity> List<T> findByProperties(Class<T> clazz,
																			   Map<String, Object> propValues) {
		return getRepository().findByProperties(clazz, NamedParameters.create(propValues));
	}

	/**
	 * 查询返回list的第一条数据，如果list为空时返回null，例如查找name="张三", age=35的员工。
	 *
	 * @param <T>
	 *            实体所属的类型
	 * @param clazz
	 *            实体所属的类
	 * @param propValues
	 *            属性值匹配条件
	 * @return 符合条件的实体列表
	 */
	public static <T extends Entity> T findFirstByProperties(Class<T> clazz,
																			  Map<String, Object> propValues) {
		List<T> result = getRepository().findByProperties(clazz,
				NamedParameters.create(propValues));
		if (result != null && !result.isEmpty()) {
			return result.get(0);
		}
		return null;
	}

	/**
	 * in查询，paramString为key,paramValues为参数key对应值的集合
	 *
	 * @param clazz
	 * @param paramString
	 * @param paramValues
	 * @return
	 * @author YixinCapital -- wujt 2016年9月18日 下午5:25:02
	 */
	public static <T extends Entity> List<T> findInSet(Class<T> clazz, String paramString,
																		Set<Object> paramValues) {
		Assert.notNull(paramString, PARAMETERS_CANNOT_BE_NULL);
		Assert.notNull(paramValues, PARAMETERS_CANNOT_BE_NULL);
		return getRepository().findInSet(clazz, paramString, paramValues);

	}

	/**
	 * NOT IN查询，paramString为key,paramValues为参数key对应值的集合
	 * 
	 * @param clazz
	 * @param paramString
	 * @param paramValues
	 * @return
	 * @author YixinCapital -- wujt 2016年9月18日 下午5:26:11
	 */
	public static <T extends Entity> List<T> findNotInSet(Class<T> clazz, String paramString,
																		   Set<Object> paramValues) {
		Assert.notNull(paramString, PARAMETERS_CANNOT_BE_NULL);
		Assert.notNull(paramValues, PARAMETERS_CANNOT_BE_NULL);
		return getRepository().findNotInSet(clazz, paramString, paramValues);
	}
}
