## 数据库

建表

```mysql
drop database coj;
create database if not exists coj;
use coj;
# json格式的字段，查看下面的json
create table comments
(
    id          bigint auto_increment primary key,
    user_id     bigint    null,
    problem_id  bigint    null,
    content     text      null,
    create_time timestamp null
);
create index problem_id on comments (problem_id);
create index user_id on comments (user_id);

create table problem
(
    id           bigint auto_increment primary key,
    title        varchar(50)  null comment '标题',
    level        varchar(10)  null comment '难度',
    description  text         null comment 'MD格式的描述',
    answer       text         null comment 'MD格式的题解',
    tags         varchar(255) null comment 'Json格式数组',
    pass         int          null comment '通过数',
    judge_config text         null comment 'Json格式的判题配置',
    judge_cases  text         null comment 'Json格式的判题样例',
    user_id      bigint       null comment '创建者',
    deleted      tinyint      null
);

create table submission
(
    id         bigint auto_increment primary key,
    problem_id bigint      null comment '题目ID',
    user_id    bigint      null comment '提交者ID',
    language   varchar(20) null comment '语言',
    code       text        null comment '提交代码',
    state      tinyint     null comment '判题状态, 0: 待判题, 1: 判题中, 2: 判题成功, 3: 判题失败',
    judge_info text        null comment 'Json格式的判题结果',
    time       timestamp   null comment '提交时间'
);
create index idx_problem_id on submission (problem_id);
create index idx_user_id on submission (user_id);

create table user
(
    id       bigint auto_increment primary key,
    account  varchar(50)  null comment '用户名唯一',
    nickname varchar(50)  null,
    password varchar(50)  null comment '加盐加密的密码',
    salt     varchar(50)  null comment '盐值',
    avatar   varchar(255) null comment '头像url',
    role     varchar(10)  null comment 'user or admin',
    constraint account unique (account)
);
```

Json格式的字段

```json
{
  "judge_config": {
    "time_limit": 1000,
    "memory_limit": 1024
  },

  "judge_cases": [
    {
      "input": "1 2",
      "output": "3"
    },
    {
      "input": "2 3",
      "output": "5"
    }
  ],

  "judge_info": {
    "state": 0,
    "message": "",
    "stderr": "",
    "useTime": 652,
    "useMemory": 102
  }
}
```