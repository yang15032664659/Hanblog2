package com.han.entity;

import java.util.Date;

import java.io.Serializable;


import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 文章表(Article)表实体类
 *
 * @author makejava
 * @since 2023-02-03 14:33:27
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
//链式编程  返回对象本身
@Accessors(chain = true)
@TableName("sg_article")
public class Article{
    @TableId
    private Long id;
    //标题
    @TableField(strategy = FieldStrategy.IGNORED)
    private String title;
    //文章内容
    @TableField(strategy = FieldStrategy.IGNORED)
    private String content;
    //文章摘要
    @TableField(strategy = FieldStrategy.IGNORED)
    private String summary;
    //所属分类id
    @TableField(strategy = FieldStrategy.IGNORED)
    private Long categoryId;

    @TableField(exist = false,strategy = FieldStrategy.IGNORED)
    private String categoryName;

    //缩略图
    @TableField(strategy = FieldStrategy.IGNORED)
    private String thumbnail;
    //是否置顶（0否，1是）
    @TableField(strategy = FieldStrategy.IGNORED)
    private String isTop;
    //状态（0已发布，1草稿）
    @TableField(strategy = FieldStrategy.IGNORED)
    private String status;
    //访问量
    @TableField(strategy = FieldStrategy.IGNORED)
    private Long viewCount;
    //是否允许评论 1是，0否
    @TableField(strategy = FieldStrategy.IGNORED)
    private String isComment;
    @TableField(strategy = FieldStrategy.IGNORED)
    private Long createBy;
    @TableField(strategy = FieldStrategy.IGNORED)
    private Date createTime;
    @TableField(strategy = FieldStrategy.IGNORED)
    private Long updateBy;
    @TableField(strategy = FieldStrategy.IGNORED)
    private Date updateTime;
    //删除标志（0代表未删除，1代表已删除）
    @TableField(strategy = FieldStrategy.IGNORED)
    private Integer delFlag;


    public Article(Long id, long viewCount) {
        this.id = id;
        this.viewCount = viewCount;
    }
}

