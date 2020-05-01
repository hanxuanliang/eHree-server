# (category)
| 列名 | 类型 | KEY | 可否为空 | 默认值 | 注释 |
| ---- | ---- | ---- | ---- | ---- | ----  |
| id | int(10) unsigned | PRI | NO |  |  |
| name | varchar(255) |  | NO |  |  |
| description | varchar(255) |  | YES |  |  |
| create_time | datetime(3) |  | YES |  |  |
| update_time | datetime(3) |  | YES |  |  |
| delete_time | datetime(3) |  | YES |  |  |
| is_root | tinyint(3) unsigned |  | NO |  |  |
| parent_id | int(10) unsigned |  | YES |  |  |
| img | varchar(255) |  | YES |  |  |
| index | int(10) unsigned |  | YES |  |  |
| online | int(10) unsigned |  | YES |  |  |
| level | int(10) unsigned |  | YES |  |  |