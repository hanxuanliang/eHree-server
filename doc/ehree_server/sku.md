# (sku)
| 列名 | 类型 | KEY | 可否为空 | 默认值 | 注释 |
| ---- | ---- | ---- | ---- | ---- | ----  |
| id | int(10) unsigned | PRI | NO |  |  |
| price | decimal(10,2) unsigned |  | NO |  |  |
| discount_price | decimal(10,2) unsigned |  | YES |  |  |
| online | tinyint(3) unsigned |  | NO |  |  |
| img | varchar(255) |  | YES |  |  |
| title | varchar(255) |  | YES |  |  |
| spu_id | int(10) unsigned |  | NO |  |  |
| create_time | datetime(3) |  | YES |  |  |
| update_time | datetime(3) |  | YES |  |  |
| delete_time | datetime(3) |  | YES |  |  |
| specs | json |  | YES |  |  |
| code | varchar(255) |  | YES |  |  |
| stock | int(10) unsigned |  | NO |  |  |
| category_id | int(10) unsigned |  | YES |  |  |
| root_category_id | int(10) unsigned |  | YES |  |  |