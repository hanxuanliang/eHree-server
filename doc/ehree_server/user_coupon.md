# (user_coupon)
| 列名 | 类型 | KEY | 可否为空 | 默认值 | 注释 |
| ---- | ---- | ---- | ---- | ---- | ----  |
| id | int(10) unsigned | PRI | NO |  |  |
| user_id | int(10) unsigned | MUL | NO |  |  |
| coupon_id | int(10) unsigned |  | NO |  |  |
| status | tinyint(3) unsigned |  | NO |  | 1:未使用，2：已使用， 3：已过期 |
| create_time | datetime(3) |  | YES |  |  |
| order_id | int(10) unsigned |  | YES |  |  |
| update_time | datetime(3) |  | YES |  |  |