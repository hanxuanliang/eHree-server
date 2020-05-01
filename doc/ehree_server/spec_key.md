# (spec_key)
| 列名 | 类型 | KEY | 可否为空 | 默认值 | 注释 |
| ---- | ---- | ---- | ---- | ---- | ----  |
| id | int(10) unsigned | PRI | NO |  |  |
| name | varchar(100) |  | NO |  |  |
| unit | varchar(30) |  | YES |  |  |
| standard | tinyint(3) unsigned |  | NO |  |  |
| create_time | datetime |  | YES |  |  |
| update_time | datetime |  | YES |  |  |
| delete_time | datetime |  | YES |  |  |
| description | varchar(255) |  | YES |  |  |