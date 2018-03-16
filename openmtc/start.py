#!/usr/bin/env python

from openmtc_app.onem2m import XAE
from openmtc_onem2m.model import Container
from openmtc_onem2m.model import AE

from random import random
import base64, io, picamera, time

class TestIPE(XAE):
	remove_registration = True
	# sensors to create
	sensors = ['camera', 'temperature']
	remote_cse = '~/in-cse-3/onem2m'
	_registered_sensors = {}

	def _on_register(self):
		self.create_server_structure()
		self.logger.debug('registered')
		self.run_forever(period=0, func=self.get_camera_data)
		# log message

	def get_camera_data(self):
		#camera data
		with picamera.PiCamera() as camera:
			camera.resolution = (640, 480)
			camera.framerate = 30
			time.sleep(2)
			start = time.time()
			stream = io.BytesIO()
			# Use the video-port for captures...
			for foo in camera.capture_continuous(stream, 'jpeg', quality=7, use_video_port=True):
				stream.seek(0)
				data = base64.b64encode(stream.read()).decode()
				self.save_data('camera', data)
				stream.seek(0)
				stream.seek(0)
				stream.truncate()

	def save_data(self, sensor, value):
		data = {
			'timestamp': time.time(),
			'value': value
		}

		self.push_content(self._registered_sensors[sensor], data)

	def create_gateway_structure(self):
		pass

	def create_server_structure(self):
		self.data_ae = self.create_application(AE(resourceName='sensor_data'), self.remote_cse)

		for sensor in self.sensors:
			container = Container(resourceName=sensor)
			data_container = self.create_container(self.data_ae.path, container, labels=sensor, max_nr_of_instances=10)
			self._registered_sensors[sensor] = data_container

if __name__ == '__main__':
	from openmtc_app.runner import AppRunner as Runner

	host = 'http://10.8.0.6:8000'
	app = TestIPE()

	Runner(app).run(host)
