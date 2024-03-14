drop database coj;
create database if not exists coj;
use coj;
# 更多信息查看 coj.json
create table user
(
    id       bigint primary key auto_increment,
    account  varchar(50) unique comment '用户名唯一',
    nickname varchar(50) comment '昵称',
    password varchar(50) comment '加盐加密的密码',
    salt     varchar(50) comment '盐值',
    avatar   varchar(255) comment '头像url',
    role     varchar(10) comment 'user or admin'
);

create table problem
(
    id           bigint primary key auto_increment,
    title        varchar(50),
    level        varchar(10),
    description  text comment 'MD格式的描述',
    answer       text comment 'MD格式的答案',
    tags         varchar(255) comment 'Json格式字符串',
    pass         int comment '通过数',

    judge_config text comment 'Json格式的判题配置',
    judge_cases  text comment 'Json格式的判题样例',
    user_id      bigint comment '创建者',

    deleted      tinyint comment '逻辑删除'
);

create table submission
(
    id         bigint primary key auto_increment,
    problem_id bigint comment '题目ID',
    user_id    bigint comment '提交者ID',

    language   varchar(20) comment '语言',
    code       text comment '提交代码',

    state      tinyint comment '判题状态, 0: 待判题, 1: 判题中, 2: 判题成功, 3: 判题失败',
    judge_info text comment 'Json格式的判题结果',

    time       timestamp comment '提交时间',

    index idx_problem_id (problem_id),
    index idx_user_id (user_id)
);

create table comments
(
    id         bigint primary key auto_increment,
    user_id    bigint comment '评论者ID',
    problem_id bigint comment '题目ID',
    content    text comment '评论内容',
    create_time       timestamp comment '评论时间'
);

insert into user values (1, 'admin', '管理员', '4ad038f69417e59b34a6f753a002c31f', '783b8e9cb50b4c789e027eec8e7f5909', null, 'admin');


