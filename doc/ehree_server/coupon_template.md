# (coupon_template)
| 列名 | 类型 | KEY | 可否为空 | 默认值 | 注释 |
| ---- | ---- | ---- | ---- | ---- | ----  |
| id | int(10) unsigned | PRI | NO |  |  |
| title | varchar(255) |  | YES |  |  |
| description | varchar(255) |  | YES |  |  |
| full_money | decimal(10,2) |  | YES |  |  |
| minus | decimal(10,2) |  | YES |  |  |
| discount | decimal(10,2) |  | YES |  | 国内多是打折，国外多是百分比 off |
| type | smallint(6) |  | NO |  | 1. 满减券 2.折扣券 3.无门槛券 4.满金额折扣券 |
| create_time | datetime(3) |  | YES |  |  |
| update_time | datetime(3) |  | YES |  |  |
| delete_time | datetime(3) |  | YES |  |  |