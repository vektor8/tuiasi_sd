import pika
import uuid
import pika.channel
import pika.spec

class TimeRpcClient:
    def __init__(self):
        self.connection = pika.BlockingConnection(pika.ConnectionParameters("localhost"))
        self.channel = self.connection.channel()

        result = self.channel.queue_declare(queue='', exclusive=True)
        self.callback_queue = result.method.queue

        self.channel.basic_consume(
            queue=self.callback_queue,
            on_message_callback=self.on_response,
            auto_ack=True
        )

        self.response = None
        self.corr_id = None

    def on_response(self, ch: pika.channel.Channel, method: pika.spec.Basic.Deliver, props: pika.spec.BasicProperties, body: bytes):
        if self.corr_id == props.correlation_id:
            self.response = body

    def get_time(self):
        self.response = None
        self.corr_id = str(uuid.uuid4())

        self.channel.basic_publish(
            exchange='',
            routing_key='rpc_queue',
            properties=pika.BasicProperties(
                reply_to=self.callback_queue,
                correlation_id=self.corr_id,
            ),
            body='get_time'
        )

        while self.response is None:
            self.connection.process_data_events()
        return self.response.decode()

if __name__ == '__main__':
    rpc_client = TimeRpcClient()
    print("Requesting current time...")
    response = rpc_client.get_time()
    print(f" [.] Got {response}")

