package com.FWRP.dto;

public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private int type;
    private String address;
    private String password;
    private String verificationToken;
    private boolean verified;
  
    public String getVerificationToken() {
      return verificationToken;
  }

  public void setVerificationToken(String verificationToken) {
      this.verificationToken = verificationToken;
  }

  public boolean isVerified() {
      return verified;
  }

  public void setVerified(boolean verified) {
      this.verified = verified;
  }
    public Long getId() {
      return id;
    }
  
    public void setId(Long id) {
      this.id = id;
    }
  
    public String getName() {
      return name;
    }
  
    public void setName(String name) {
      this.name = name;
    }
  
    public String getEmail() {
      return email;
    }
  
    public void setEmail(String email) {
      this.email = email;
    }
  
    public int getType() {
      return type;
    }
  
    public void setType(int type) {
      this.type = type;
    }
  
    public String getAddress() {
      return address;
    }
  
    public void setAddress(String address) {
      this.address = address;
    }
  
    public String getPassword() {
      return password;
    }
  
    public void setPassword(String password) {
      this.password = password;
    }
}
