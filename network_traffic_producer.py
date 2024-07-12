import time
import json
import random
from kafka import KafkaProducer

producer = KafkaProducer(
    bootstrap_servers=['localhost:9092'],
    value_serializer=lambda v: json.dumps(v).encode('utf-8')
)

def generate_network_traffic():
    while True:
        traffic_data = {
            'source_ip': f"192.168.1.{random.randint(1, 254)}",
            'destination_ip': f"192.168.1.{random.randint(1, 254)}",
            'bytes_sent': random.randint(50, 10000),
            'timestamp': int(time.time())
        }
        producer.send('network_traffic', value=traffic_data)
        time.sleep(0.5)

if __name__ == "__main__":
    generate_network_traffic()
