# 数据库初始化

-- 创建库
create database if not exists generator_db;

-- 切换库
use generator_db;

-- 用户表
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_account` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '账号',
  `user_password` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `user_name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户昵称',
  `user_avatar` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户头像',
  `user_profile` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '个人简介',
  `user_role` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'user' COMMENT '用户角色：user/admin',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_delete` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除，0未删除，1已删除',
  PRIMARY KEY (`id`),
  index idx_userAccount (user_account)
) ENGINE=InnoDB AUTO_INCREMENT=1746771169433956359 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户';

-- 代码生成器表
create table if not exists generator
(
    id          bigint auto_increment comment 'id' primary key,
    name        varchar(128)                       null comment '名称',
    description text                               null comment '描述',
    base_package varchar(128)                       null comment '基础包',
    version     varchar(128)                       null comment '版本',
    author      varchar(128)                       null comment '作者',
    tags        varchar(1024)                      null comment '标签列表（json 数组）',
    picture     varchar(256)                       null comment '图片',
    file_config  text                               null comment '文件配置（json字符串）',
    model_config text                               null comment '模型配置（json字符串）',
    dist_path    text                               null comment '代码生成器产物路径',
    status      int      default 0                 not null comment '状态',
    user_id      bigint                             not null comment '创建用户 id',
    create_time  datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time  datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    is_delete    tinyint  default 0                 not null comment '是否删除',
    index idx_userId (user_id)
    ) comment '代码生成器' collate = utf8mb4_unicode_ci;

-- 模拟用户数据
INSERT INTO generator_db.user (id, user_account, user_password, user_name, user_avatar, user_profile, user_role) VALUES (1, 'admin', 'b0dd3697a192885d7c055db46155b26a', '管理员', 'https://gw.alipayobjects.com/zos/rmsportal/BiazfanxmamNRoxxVxka.png', '我有一头小毛驴我从来也不骑', 'admin');
INSERT INTO generator_db.user (id, user_account, user_password, user_name, user_avatar, user_profile, user_role) VALUES (2, 'juzi', 'b0dd3697a192885d7c055db46155b26a', '橘子', 'https://gw.alipayobjects.com/zos/rmsportal/BiazfanxmamNRoxxVxka.png', '我有一头小毛驴我从来也不骑', 'user');

-- 模拟代码生成器数据
INSERT INTO generator_db.generator (id, name, description, base_package, version, author, tags, picture, file_config, model_config, dist_path, status, user_id) VALUES (1, 'ACM 模板项目', 'ACM 模板项目生成器', 'com.xyh', '1.0', '橘子', '["Java"]', 'https://pic.yupi.icu/1/_r0_c1851-bf115939332e.jpg', '{}', '{}', null, 0, 1);
INSERT INTO generator_db.generator (id, name, description, base_package, version, author, tags, picture, file_config, model_config, dist_path, status, user_id) VALUES (2, 'Spring Boot 初始化模板', 'Spring Boot 初始化模板项目生成器', 'com.xyh', '1.0', '橘子', '["Java"]', 'https://pic.yupi.icu/1/_r0_c0726-7e30f8db802a.jpg', '{}', '{}', null, 0, 1);
INSERT INTO generator_db.generator (id, name, description, base_package, version, author, tags, picture, file_config, model_config, dist_path, status, user_id) VALUES (3, '橘子外卖', '橘子外卖项目生成器', 'com.xyh', '1.0', '橘子', '["Java", "前端"]', 'https://pic.yupi.icu/1/_r1_c0cf7-f8e4bd865b4b.jpg', '{}', '{}', null, 0, 1);
INSERT INTO generator_db.generator (id, name, description, base_package, version, author, tags, picture, file_config, model_config, dist_path, status, user_id) VALUES (4, '橘子用户中心', '橘子用户中心项目生成器', 'com.xyh', '1.0', '橘子', '["Java", "前端"]', 'https://pic.yupi.icu/1/_r1_c1c15-79cdecf24aed.jpg', '{}', '{}', null, 0, 1);
INSERT INTO generator_db.generator (id, name, description, base_package, version, author, tags, picture, file_config, model_config, dist_path, status, user_id) VALUES (5, '橘子商城', '橘子商城项目生成器', 'com.xyh', '1.0', '橘子', '["Java", "前端"]', 'https://pic.yupi.icu/1/_r1_c0709-8e80689ac1da.jpg', '{}', '{}', null, 0, 1);