# (coupon)
| 列名 | 类型 | KEY | 可否为空 | 默认值 | 注释 |
| ---- | ---- | ---- | ---- | ---- | ----  |
| id | int(10) unsigned | PRI | NO |  |  |
| title | varchar(255) |  | YES |  |  |
| start_time | datetime |  | YES |  |  |
| end_time | datetime |  | YES |  |  |
| description | varchar(255) |  | YES |  |  |
| full_money | decimal(10,2) |  | YES |  |  |
| minus | decimal(10,2) |  | YES |  |  |
| rate | decimal(10,2) |  | YES |  |  |
| type | smallint(6) |  | NO |  | 1. 满减券 2.折扣券 3.无门槛券 4.满金额折扣券 |
| create_time | datetime(3) |  | YES |  |  |
| update_time | datetime(3) |  | YES |  |  |
| delete_time | datetime(3) |  | YES |  |  |
| activity_id | int(10) unsigned |  | YES |  |  |
| remark | varchar(255) |  | YES |  |  |
| whole_store | tinyint(3) unsigned |  | YES |  |  |