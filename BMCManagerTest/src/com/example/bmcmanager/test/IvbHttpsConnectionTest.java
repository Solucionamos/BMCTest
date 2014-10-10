package com.example.bmcmanager.test;

import java.io.IOException;
import java.net.ConnectException;
import java.util.List;

import org.apache.http.auth.AuthenticationException;

import android.test.AndroidTestCase;
import com.solucionamos.bmcconnection.IvbHttpsConnection;
import com.solucionamos.bmcconnection.Sensor;

public class IvbHttpsConnectionTest extends AndroidTestCase {

	// BMC IP address
	private static final String BMC_IP = "10.35.101.51";
	private static IvbHttpsConnection conn;

	// create a new connection to BMC
	public void setUp() {
		conn = new IvbHttpsConnection(BMC_IP, "lenovo", "len0vO");
		try {
			conn.connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// disconnect from BMC
	public void tearDown() {
		try {
			conn.disconnect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// throw Exception if host IP is invalid
	public void testInvalidHostIp() throws Exception {
		Exception ex = null;
		IvbHttpsConnection conn2 = new IvbHttpsConnection("255.255.255.255",
				"", "");

		try {
			conn2.connect();
			conn2.disconnect();
		} catch (Exception e) {
			ex = e;
		}

		assertTrue(ex instanceof ConnectException);
	}

	// throw Exception if username/password is invalid
	public void testInvalidAuthentitcation() throws Exception {
		Exception ex = null;
		IvbHttpsConnection conn2 = new IvbHttpsConnection(BMC_IP, "nologin", "");

		try {
			conn2.connect();
			conn2.disconnect();
		} catch (Exception e) {
			ex = e;
		}

		assertTrue(ex instanceof AuthenticationException);
	}

	// successfully get fan data
	public void testGetFans() throws Exception {
		List<Sensor> fans = conn.getSensors(Sensor.TYPE_FAN);
		assertTrue(fans.get(0) instanceof Sensor);
		assertEquals(fans.get(0).getUnits(), "RPM");
	}

	// successfully get temperature data
	public void testGetTemperatures() throws Exception {
		List<Sensor> temperatures = conn.getSensors(Sensor.TYPE_TEMPERATURE);
		assertTrue(temperatures.get(0) instanceof Sensor);
		assertEquals(temperatures.get(0).getUnits(), "C");
	}

	// successfully get voltage data
	public void testGetVoltages() throws Exception {
		List<Sensor> voltages = conn.getSensors(Sensor.TYPE_VOLTAGE);
		assertTrue(voltages.get(0) instanceof Sensor);
		assertEquals(voltages.get(0).getUnits(), "V");
	}
}
