public static final String AES = "AES";
// This method generates a random key and stores in
// login_key table In the full program, let us see whole
// connection details
private void oneTimeKeyGeneration()
{
	try {
		KeyGenerator keyGen = KeyGenerator.getInstance(AES);
		keyGen.init(128);
		SecretKey sk = keyGen.generateKey();
		String key = byteArrayToHexString(sk.getEncoded());
		System.out.println("key:" + key);
		getAndStoreLoginKey(key);
	}
	catch (Exception ex) {
		System.out.println(ex.getMessage());
	}
}
private static String byteArrayToHexString(byte[] b)
{
	StringBuffer sb = new StringBuffer(b.length * 2);
	for (int i = 0; i < b.length; i++) {
		int v = b[i] & 0xff;
		if (v < 16) {
			sb.append('0');
		}
		sb.append(Integer.toHexString(v));
	}
	return sb.toString().toUpperCase();
}

private void getAndStoreLoginKey(String key)
{

	try {
		Class.forName("com.mysql.cj.jdbc.Driver")
			.newInstance();
		// Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(
			"jdbc:mysql://localhost:3306/geeksforgeeks?useSSL=false",
			"root", "admin");

		String sql
			= "INSERT INTO login_key(keyValue) VALUES(?)";

		PreparedStatement pst = conn.prepareStatement(sql);

		pst.setString(1, key);
		pst.executeUpdate();
	}
	catch (ClassNotFoundException ex) {
		Logger
			.getLogger(
				EncryptAndStorePassword.class.getName())
			.log(Level.SEVERE, null, ex);
	}
	catch (SQLException ex) {
		System.out.println("Exception.." + ex.getMessage());
	}
	catch (InstantiationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	catch (IllegalAccessException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
