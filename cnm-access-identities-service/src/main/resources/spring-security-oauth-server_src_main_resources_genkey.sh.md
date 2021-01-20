```shell
keytool -genkey -alias jwt -keyalg RSA -keysize 2048 -keystore jwt.jks -validity 365 -keypass isfong -storepass isfong
您的名字与姓氏是什么?
  [Unknown]:  isfong
您的组织单位名称是什么?
  [Unknown]:  yuhao
您的组织名称是什么?
  [Unknown]:  yuhao
您所在的城市或区域名称是什么?
  [Unknown]:  GZ
您所在的省/市/自治区名称是什么?
  [Unknown]:  GD
该单位的双字母国家/地区代码是什么?
  [Unknown]:  CN
CN=isfong, OU=yuhao, O=yuhao, L=GZ, ST=GD, C=CN是否正确?
  [否]:  y
ls
jwt.jks
```

```shell
keytool -list -rfc -keystore jwt.jks | openssl x509 -inform pem -pubkey
输入密钥库口令:  isfong
-----BEGIN PUBLIC KEY-----
MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtvw6JNVvt8rbMSK9M/yH
Bzi+LWwG+OlRAPFAcEWXndKDrMCODVfN89Od/xbsjSHnBtX8BuFUPkB0mXu4xwZO
BO4s9qYL//GOCUwS/CSLu8BfuuRvxnkjnEOFa7ayrxKPduWYLZu/02sl2mBDCFpd
kWkemOgBwd/gKzYwRdpToVro6pnQf2GG561qap7SIY6v043vRPAzxlFPu080NXzV
FHVh1v8kxII9xEhnFVJJPyPdDw7zzQo7/z8TfFsF0E8rU/xDwLuuxvsgd/dll4p9
CbEsx31PPLx4rcu1SdtPgMtlsk4IThKHY3PN29heD6AGdU02jO+7JbDU67I4Vlj1
nwIDAQAB
-----END PUBLIC KEY-----
-----BEGIN CERTIFICATE-----
MIIDTzCCAjegAwIBAgIEMDWjUzANBgkqhkiG9w0BAQsFADBYMQswCQYDVQQGEwJD
TjELMAkGA1UECBMCR0QxCzAJBgNVBAcTAkdaMQ4wDAYDVQQKEwV5dWhhbzEOMAwG
A1UECxMFeXVoYW8xDzANBgNVBAMTBmlzZm9uZzAeFw0yMDExMjgwODE5MjhaFw0y
MTExMjgwODE5MjhaMFgxCzAJBgNVBAYTAkNOMQswCQYDVQQIEwJHRDELMAkGA1UE
BxMCR1oxDjAMBgNVBAoTBXl1aGFvMQ4wDAYDVQQLEwV5dWhhbzEPMA0GA1UEAxMG
aXNmb25nMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtvw6JNVvt8rb
MSK9M/yHBzi+LWwG+OlRAPFAcEWXndKDrMCODVfN89Od/xbsjSHnBtX8BuFUPkB0
mXu4xwZOBO4s9qYL//GOCUwS/CSLu8BfuuRvxnkjnEOFa7ayrxKPduWYLZu/02sl
2mBDCFpdkWkemOgBwd/gKzYwRdpToVro6pnQf2GG561qap7SIY6v043vRPAzxlFP
u080NXzVFHVh1v8kxII9xEhnFVJJPyPdDw7zzQo7/z8TfFsF0E8rU/xDwLuuxvsg
d/dll4p9CbEsx31PPLx4rcu1SdtPgMtlsk4IThKHY3PN29heD6AGdU02jO+7JbDU
67I4Vlj1nwIDAQABoyEwHzAdBgNVHQ4EFgQU5NKj+bWr/pNTB+Tp7Peow8WbYyYw
DQYJKoZIhvcNAQELBQADggEBAIZEZ2F5ZdvKyFTgV+BlLEI/taNxSO1H6eQxWw3o
Jy2d7o6+EHXSq0GgJXj98occkpcN+fgUC1ygdHD2Pzm4uyIXACXUpazlz1lOeeTi
Yu9IR0noYW4iC3DYv+yAUC+IePAZkvh9OBibsIcih1vGH5XNrjGEgdjp5VfRqPa5
r8sWURrb0KsB0uPSbZBBom1cQb8X0Q3p+8W1k7AWkNDuG454pwLv8iZui+QhG3ET
ogaD7rhFEeOEtNIsH3vtTXt5uQj3sUq1EevxWDRmKiV3bQxQ7y1CJw9jTSXb9Kn8
z+m1e3h1GpSK2l6MGZPx23iayevvLCIv1JLwgi7226CKvcA=
-----END CERTIFICATE-----
```