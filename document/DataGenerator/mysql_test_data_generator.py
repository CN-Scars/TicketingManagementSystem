
import random
from faker import Faker
import mysql.connector

# Establish MySQL Connection
conn = mysql.connector.connect(
    host="localhost",
    user="root",
    password="password",
    database="attraction_ticket_sales"
)
cursor = conn.cursor()

# Initialize Faker
fake = Faker()

# Generate Users
for _ in range(1000):  # Adjust the range for the number of users you want
    username = fake.user_name()
    email = fake.email()
    age = random.randint(18, 80)
    cursor.execute("INSERT INTO user (Username, Email, Age) VALUES (%s, %s, %s)", (username, email, age))

# Generate Sellers
for _ in range(100):  # Adjust the range for the number of sellers
    name = fake.company()
    cursor.execute("INSERT INTO seller (Name) VALUES (%s)", (name,))

# Generate Tickets
for _ in range(500):  # Adjust the range for the number of tickets
    price = round(random.uniform(10.00, 100.00), 2)
    stock = random.randint(1, 100)
    ticket_name = fake.catch_phrase()
    cursor.execute("INSERT INTO ticket (Price, Stock, TicketName) VALUES (%s, %s, %s)", (price, stock, ticket_name))

conn.commit()

# Generate Orders
cursor.execute("SELECT UserID FROM user")
user_ids = [item[0] for item in cursor.fetchall()]

cursor.execute("SELECT TicketID FROM ticket")
ticket_ids = [item[0] for item in cursor.fetchall()]

cursor.execute("SELECT SellerId FROM seller")
seller_ids = [item[0] for item in cursor.fetchall()]

for _ in range(10000):  # Adjust for the number of orders
    user_id = random.choice(user_ids)
    ticket_id = random.choice(ticket_ids)
    seller_id = random.choice(seller_ids)
    quantity = random.randint(1, 5)
    cursor.execute("SELECT Price FROM ticket WHERE TicketID = %s", (ticket_id,))
    price = cursor.fetchone()[0]
    total_price = round(quantity * price, 2)
    state = random.randint(0, 1)

    cursor.execute("INSERT INTO `order` (UserID, TicketID, Quantity, TotalPrice, SellerId, State) VALUES (%s, %s, %s, %s, %s, %s)",
                   (user_id, ticket_id, quantity, total_price, seller_id, state))

conn.commit()

# Close Connection
cursor.close()
conn.close()
