package com.han.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleByIdVo {

    private Long id;
    //标题

    private String title;
    //文章内容

    private String content;
    //文章摘要

    private String summary;
    //所属分类id

    private Long categoryId;


    private String categoryName;

    //缩略图

    private String thumbnail;
    //是否置顶（0否，1是）

    private String isTop;
    //状态（0已发布，1草稿）

    private String status;
    //访问量

    private Long viewCount;
    //是否允许评论 1是，0否

    private String isComment;

    private Long createBy;

    private Date createTime;

    private Long updateBy;

    private Date updateTime;
    //删除标志（0代表未删除，1代表已删除）

    private Integer delFlag;

    private List<Long> tags;

//    "categoryId":"1",
//    "content":"xxxxxxx",
//    "createBy":"1",
//    "createTime":"2022-08-28 15:15:46",
//    "delFlag":0,
//    "id":"10",
//    "isComment":"0",
//    "isTop":"1",
//    "status":"0",
//    "summary":"啊实打实",
//    "tags":[
//    "1",
//    "4",
//    "5"
//    ],
//    "thumbnail":"https://sg-blog-oss.oss-cn-beijing.aliyuncs.com/2022/08/28/7659aac2b74247fe8ebd9e054b916dbf.png",
//    "title":"委屈饿驱蚊器",
//    "updateBy":"1",
//    "updateTime":"2022-08-28 15:15:46",
//    "viewCount":"0"
}
