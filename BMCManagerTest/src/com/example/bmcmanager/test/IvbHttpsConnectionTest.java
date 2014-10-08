package com.example.bmcmanager.test;

import java.net.ConnectException;
import java.util.List;

import org.apache.http.auth.AuthenticationException;

import android.test.AndroidTestCase;
import com.solucionamos.bmcconnection.Fan;
import com.solucionamos.bmcconnection.IvbHttpsConnection;

public class IvbHttpsConnectionTest extends AndroidTestCase {

	// BMC IP address
	private static final String BMC_IP = "10.35.101.51";

	// throw Exception if host IP is invalid
	public void testInvalidHostIp() {
		Exception ex = null;
		IvbHttpsConnection conn = new IvbHttpsConnection("255.255.255.255", "",
				"");

		try {
			conn.connect();
			conn.disconnect();
		} catch (Exception e) {
			ex = e;
		}

		assertTrue(ex instanceof ConnectException);
	}

	// throw Exception if username/password is invalid
	public void testInvalidAuthentitcation() throws Exception {
		Exception ex = null;
		IvbHttpsConnection conn = new IvbHttpsConnection(BMC_IP, "nologin", "");

		try {
			conn.connect();
			conn.disconnect();
		} catch (Exception e) {
			ex = e;
		}

		assertTrue(ex instanceof AuthenticationException);
	}

	// successfully connect to the BMC
	public void testConnectToBMC() throws Exception {
		IvbHttpsConnection conn = new IvbHttpsConnection(BMC_IP, "lenovo",
				"len0vO");

		conn.connect();
		conn.disconnect();
	}

	// successfully get fan data
	public void testGetFans() throws Exception {
		IvbHttpsConnection conn = new IvbHttpsConnection(BMC_IP, "lenovo",
				"len0vO");

		conn.connect();
		List<Fan> fans = conn.getFans();
		assertTrue(fans.get(0) instanceof Fan);
		assertEquals(fans.get(0).getUnits(), "RPM");
		conn.disconnect();
	}
}
