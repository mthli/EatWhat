#!/usr/bin/python3
import sqlite3

# 建立数据库连接
conn = sqlite3.connect("restaurant.db")
cur = conn.cursor();

# 创建数据表
table = "CREATE TABLE restaurant (RESTAURANT text, PATH text);"
print(table)
cur.execute(table)
conn.commit()

# 开始读取文本
f = open("restaurant.txt", "r")
lines = f.readlines()
# 循环
for line in lines:
    # 处理每一行中间的空格
    line = line.strip('\n')
    info = line.split(" ")
    restaurant = info[0]
    path = info[1]
    # 开始拼凑插入语句
    insert = "INSERT INTO restaurant (RESTAURANT, PATH) VALUES ('" + restaurant + "', '" + path + "');"
    print(insert)
    # 开始执行插入
    cur.execute(insert)
    conn.commit()

# 插入完毕后关闭数据库连接
cur.close()
