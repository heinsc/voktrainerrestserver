package de.heinsc.vokabeltraineronline.postgresrestadapter.vokabeltraineronline.postgresrestadapter.appuser;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import java.util.Date;

@Entity
public class AppUser {

    @Id
    @GeneratedValue
    private Long id;
    private String eMailAddress;
    @Transient
    private byte[] passwordDecodedScnd;
	private byte[] passwordDecoded;
    private Date lastLogin;
    @Transient
    private String resultMessage;

    // avoid this "No default constructor for entity"
    public AppUser() {
    }

    public AppUser(String anEMailAddress, byte[] bs) {
        this.eMailAddress = anEMailAddress;
        this.passwordDecoded = bs;
        this.lastLogin = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + eMailAddress + '\'' +
                ", last login date='" + lastLogin + '\'' +
                '}';
    }

    public byte[] getPasswordDecodedScnd() {
		return passwordDecodedScnd;
	}
	public Date getLastLogin() {
		return lastLogin;
	}

	public byte[] getPasswordDecoded() {
		return passwordDecoded;
	}

	public void setPasswordDecoded(byte[] passwordDecoded) {
		this.passwordDecoded = passwordDecoded;
	}

	public String geteMailAddress() {
		return eMailAddress;
	}

	public void seteMailAddress(String eMailAddress) {
		this.eMailAddress = eMailAddress;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public String getResultMessage() {
		return resultMessage;
	}

}
