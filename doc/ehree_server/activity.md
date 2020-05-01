# (activity)
| 列名 | 类型 | KEY | 可否为空 | 默认值 | 注释 |
| ---- | ---- | ---- | ---- | ---- | ----  |
| id | int(10) unsigned | PRI | NO |  |  |
| title | varchar(60) |  | YES |  |  |
| description | varchar(255) |  | YES |  |  |
| start_time | datetime(3) |  | NO |  |  |
| end_time | datetime(3) |  | NO |  |  |
| create_time | datetime(3) |  | YES |  |  |
| update_time | datetime(3) |  | YES |  |  |
| delete_time | datetime(3) |  | YES |  |  |
| remark | varchar(60) |  | YES |  |  |
| online | tinyint(3) unsigned |  | YES |  |  |
| entrance_img | varchar(255) |  | YES |  |  |
| internal_top_img | varchar(255) |  | YES |  |  |
| name | varchar(20) |  | NO |  |  |