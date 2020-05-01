# (user)
| 列名 | 类型 | KEY | 可否为空 | 默认值 | 注释 |
| ---- | ---- | ---- | ---- | ---- | ----  |
| id | int(10) unsigned | PRI | NO |  |  |
| openid | varchar(50) | UNI | YES |  |  |
| nickname | varchar(60) |  | YES |  |  |
| unify_uid | int(10) |  | YES |  |  |
| email | varchar(255) |  | YES |  |  |
| password | varchar(255) |  | YES |  |  |
| mobile | varchar(30) |  | YES |  |  |
| wx_profile | json |  | YES |  |  |
| create_time | datetime(3) |  | YES |  |  |
| update_time | datetime(3) |  | YES |  |  |
| delete_time | datetime(3) |  | YES |  |  |