# This python script deletes all tables
# in the sqlite database and then recreates them

import sqlite3
conn = sqlite3.connect('database.sqlite')

c = conn.cursor()

# Names of tables to be reused
TASK_TABLE_NAME = "Task"
SECTION_TABLE_NAME = "Section"

# Drops all tables
print('Dropping all tables.')
c.execute("DROP TABLE IF EXISTS " + TASK_TABLE_NAME)
c.execute("DROP TABLE IF EXISTS " + SECTION_TABLE_NAME)

# Enables foreign keys
c.execute("PRAGMA foreign_keys = ON;")

# Re-Creates Tables following schema
print('Re-creating all tables.')
c.execute("""CREATE TABLE Section(
				id INTEGER PRIMARY KEY,
				name TEXT);
""")

c.execute("""CREATE TABLE Task(
				id INTEGER PRIMARY KEY,
				descr TEXT NOT NULL,
				datetimecreated TIMESTAMP NOT NULL DEFAULT (strftime('%s','now')),
				datecompleted TIMESTAMP,
				completed INTEGER NOT NULL DEFAULT 0,
				sectionid INTEGER,
				FOREIGN KEY(sectionid) REFERENCES Section(id) ON DELETE CASCADE ON UPDATE CASCADE);
""")

# # Populates tables with some boring data
print('Populating tables with test data.')
sections = ['Default', 'Section 1', 'Section 2']
for x in sections:
	c.execute("INSERT INTO Section(name) VALUES ('%s');" % x)

for n in range(1,10):
	c.execute("INSERT INTO Task(descr, sectionid) VALUES ('Task %d', 1);" % n)


conn.commit()
conn.close()
