package com.yinx.hjpa.entity;

import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***
 * 业务基类
 * 
 * @author seany
 */
@MappedSuperclass
public abstract class BZBaseEntiy extends BaseEntity {
    protected final static Logger logger = LoggerFactory.getLogger(BZBaseEntiy.class);
    private static final long serialVersionUID = 8882145542383345037L;

    public BZBaseEntiy() {
        super();
    }

    @Id
    @Column(name = "ID", length = 50)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Version
    @Column(name = "VERSION")
    private Integer version = 0;

    /***
     * 创建人ID
     */
    @Column(name = "CREATOR_ID")
    private String creatorId = "admin";

    /**
     * 创建人姓名
     */
    @Column(name = "CREATOR_NAME")
    private String creatorName = "系统";

    /**
     * 创建人所属部门id
     */
    @Column(name = "CREATOR_DEPARTMENT_ID")
    private Long creatorDepartmentId = 100L;

    /**
     * 创建人所属部门名称
     */
    @Column(name = "CREATOR_DEPARTMENT_NAME")
    private String creatorDepartmentName = "100";

    /***
     * 创建时间
     */
    @Column(name = "CREATE_TIME")
    private Date createTime;

    /***
     * 修改人域账号
     */
    @Column(name = "UPDATOR_ID")
    private String updatorId;

    /**
     * 修改人姓名
     */
    @Column(name = "UPDATOR_NAME")
    private String updatorName;

    /**
     * 修改人所属部门id
     */
    @Column(name = "UPDATOR_DEPARTMENT_ID")
    private Long updatorDepartmentId;

    /**
     * 修改人所属部门名称
     */
    @Column(name = "UPDATOR_DEPARTMENT_NAME")
    private String updatorDepartmentName;

    /***
     * 修改时间
     */
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    /***
     * 是否删除状态，true为已删除，false为未删除
     */
    @Column(name = "IS_DELETED")
    private Boolean deleted = false;

    /**
     * 业务流水号ID
     */
    @Column(name = "BZ_ID", length = 50)
    private String bzId;

    /**
     * 获得实体的标识
     * 
     * @return 实体的标识
     */
    public String getId() {
        return id;
    }

    /**
     * 设置实体的标识
     * 
     * @param id
     *            要设置的实体标识
     */
    public void setId(String id) {
        this.id = id;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getUpdatorId() {
        return updatorId;
    }

    public void setUpdatorId(String updatorId) {
        this.updatorId = updatorId;
    }

    /**
     * 获得实体的版本号。持久化框架以此实现乐观锁。
     * 
     * @return 实体的版本号
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * 设置实体的版本号。持久化框架以此实现乐观锁。
     * 
     * @param version
     *            要设置的版本号
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean isDeleted) {
        this.deleted = isDeleted;
    }

    public Date getCreateTime() {
        if (createTime == null) {
            return new Date();
        }
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        if (createTime == null) {
            this.createTime = new Date();
        } else {
            this.createTime = createTime;
        }
    }

    public Date getUpdateTime() {
        if (updateTime == null) {
            return new Date();
        }
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        if (updateTime == null) {
            this.updateTime = new Date();
        } else {
            this.updateTime = updateTime;
        }
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getUpdatorName() {
        return updatorName;
    }

    public void setUpdatorName(String updatorName) {
        this.updatorName = updatorName;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Long getCreatorDepartmentId() {
        return creatorDepartmentId;
    }

    public void setCreatorDepartmentId(Long creatorDepartmentId) {
        this.creatorDepartmentId = creatorDepartmentId;
    }

    public String getCreatorDepartmentName() {
        return creatorDepartmentName;
    }

    public void setCreatorDepartmentName(String creatorDepartmentName) {
        this.creatorDepartmentName = creatorDepartmentName;
    }

    public Long getUpdatorDepartmentId() {
        return updatorDepartmentId;
    }

    public void setUpdatorDepartmentId(Long updatorDepartmentId) {
        this.updatorDepartmentId = updatorDepartmentId;
    }

    public String getUpdatorDepartmentName() {
        return updatorDepartmentName;
    }

    public void setUpdatorDepartmentName(String updatorDepartmentName) {
        this.updatorDepartmentName = updatorDepartmentName;
    }

    public String getBzId() {
        return bzId;
    }

    public void setBzId(String bzId) {
        this.bzId = bzId;
    }

    /**
     * 创建方法
     * 
     * @return
     */
    public String create() {
        this.setCreateTime(new Date());
        this.setDeleted(false);
        this.setUpdateTime(new Date());
        this.save();
        return this.id;
    }

    /**
     * 修改方法
     * 
     * @return
     */
    public String update() {
        this.setUpdateTime(new Date());
        this.save();
        return this.id;
    }

    /**
     * 逻辑删除方法
     */
    public void logicRemove() {
        this.setDeleted(true);
        this.update();
    }

    /**
     * 物理删除方法
     */
    public void remove() {
        super.remove();
    }
}