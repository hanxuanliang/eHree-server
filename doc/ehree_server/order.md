# (order)
| 列名 | 类型 | KEY | 可否为空 | 默认值 | 注释 |
| ---- | ---- | ---- | ---- | ---- | ----  |
| id | int(10) unsigned | PRI | NO |  |  |
| order_no | varchar(20) | UNI | YES |  |  |
| user_id | int(10) unsigned |  | YES |  | user表外键 |
| total_price | decimal(10,2) |  | YES |  |  |
| total_count | int(11) unsigned |  | YES |  |  |
| create_time | datetime(3) |  | YES |  |  |
| delete_time | datetime(3) |  | YES |  |  |
| update_time | datetime(3) |  | YES |  |  |
| expired_time | datetime(3) |  | YES |  |  |
| placed_time | datetime(3) |  | YES |  |  |
| snap_img | varchar(255) |  | YES |  |  |
| snap_title | varchar(255) |  | YES |  |  |
| snap_items | json |  | YES |  |  |
| snap_address | json |  | YES |  |  |
| prepay_id | varchar(255) |  | YES |  |  |
| final_total_price | decimal(10,2) |  | YES |  |  |
| status | tinyint(3) unsigned |  | YES |  |  |