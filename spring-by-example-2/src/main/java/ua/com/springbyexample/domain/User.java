package ua.com.springbyexample.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA. User: orezchykov Date: 25.09.12 Time: 17:54 To
 * change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "user")
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -633261006252739756L;

	@Id
	@Column(name = "email", length = 32)
	private String email;

	@Column(name = "password", length = 255)
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
