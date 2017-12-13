# EncryptTool
## Android RSA/AES 加密解密测试
![](https://raw.githubusercontent.com/weefree/EncryptTool/master/doc/pic.png)
## AES
- AES的区块长度固定为128，支持 128, 192 和 256 位（16、24、32个字符）的秘钥
- Android 运行环境支持 128、192、256 秘钥长度
- Java jre 默认只支持 128（16字符）秘钥，如需支持 192,256，需要安装 [JCE](http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html)

## RSA
- RSAUtil 工具类使用私钥类型为 PKCS8
- PKCS1 私钥生成：openssl genrsa -out rsa_private_key.pem 1024
- PKCS1 转 PKCS8私钥：openssl pkcs8 -topk8 -inform PEM -in rsa_private_key.pem -outform pem -nocrypt -out pkcs8_private_key.pem
