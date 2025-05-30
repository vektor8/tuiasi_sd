import pika
import pika.connection
from retry import retry


class RabbitMq:
    config = {
        'host': '192.168.23.129',
        'port': 5672,
        'username': 'student',
        'password': 'student',
        'exchange': 'stackapp.direct',
        'routing_key': 'stackapp.routingkey1',
        'queue': 'stackapp.queue'
    }
    credentials = pika.PlainCredentials(config['username'], config['password'])
    parameters = pika.ConnectionParameters(host=config["host"], 
                                           port=config["port"], 
                                           credentials=credentials)

    def __init__(self, ui):
        self.ui = ui

    def on_received_message(self, blocking_channel, deliver, properties,
                            message):
        result = message.decode('utf-8')
        blocking_channel.confirm_delivery()
        try:
            variable, response = result.split('~')
            self.ui.set_response(variable, response)
        except Exception as e:
            print(e)
            print("wrong data format")
        finally:
            blocking_channel.stop_consuming()

    @retry(pika.exceptions.AMQPConnectionError, delay=5, jitter=(1, 3))
    def receive_message(self):
        # automatically close the connection
        with pika.BlockingConnection(self.parameters) as connection:
            # automatically close the channel
            with connection.channel() as channel:
                channel.basic_consume(self.config['queue'],
                                      self.on_received_message)
                try:
                    channel.start_consuming()
                # Don't recover connections closed by server
                except pika.exceptions.ConnectionClosedByBroker:
                    print("Connection closed by broker.")
                # Don't recover on channel errors
                except pika.exceptions.AMQPChannelError:
                    print("AMQP Channel Error")
                # Don't recover from KeyboardInterrupt
                except KeyboardInterrupt:
                    print("Application closed.")

    def send_message(self, message):
        # automatically close the connection
        with pika.BlockingConnection(self.parameters) as connection:
            # automatically close the channel
            with connection.channel() as channel:
                self.clear_queue(channel)
                channel.basic_publish(exchange=self.config['exchange'],
                                      routing_key=self.config['routing_key'],
                                      body=message)

    def clear_queue(self, channel):
        channel.queue_purge(self.config['queue'])
