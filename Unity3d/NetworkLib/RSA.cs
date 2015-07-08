using System;
using System.Collections.Generic;
using System.Text;
using System.Security.Cryptography;
using UnityEngine;

namespace NetworkLib.Cryptography
{
    public class RSA
    {
        public class PublicKey
        {
            byte[] m_modulus;
            byte[] m_exponent;

            public PublicKey(byte[] modulus, byte[] exponent)
            {
                m_modulus = modulus;
                m_exponent = exponent;
            }

            public static implicit operator RSAParameters(PublicKey key)
            {
                RSAParameters rsaParas = new RSAParameters();
                rsaParas.Modulus = key.m_modulus;
                rsaParas.Exponent = key.m_exponent;

                return rsaParas;
            }
        }

        public class PrivateKey
        {
            RSAParameters m_parameters;
            public PrivateKey(RSAParameters parameters)
            {
                m_parameters = parameters;
            }

            public static implicit operator RSAParameters(PrivateKey key)
            {
                return key.m_parameters;
            }
        }

        public class KeyPair
        {
            public PublicKey publicKey;
            public PrivateKey privateKey;

            public KeyPair()
            {
                // 개인키 생성
                RSAParameters privateParam = System.Security.Cryptography.RSA.Create().ExportParameters(true);

                publicKey = new PublicKey(privateParam.Modulus, privateParam.Exponent);
                privateKey = new PrivateKey(privateParam);
            }
        }

        static public KeyPair CreateRSAKey()
        {
            return new KeyPair();
        }

        static public byte[] Encryption(byte[] data, PublicKey key, bool DoOAEPPadding)
        {
            try
            {
                byte[] encryptedData = null;
                using (RSACryptoServiceProvider rsa = new RSACryptoServiceProvider())
                {
                    rsa.ImportParameters(key);
                    encryptedData = rsa.Encrypt(data, DoOAEPPadding);
                }
                return encryptedData;
            }
            catch (CryptographicException e)
            {
                Debug.Log(e.Message);
            }

            return null;
        }

        static public byte[] Encryption(string data, PublicKey key, bool DoOAEPPadding)
        {
            return Encryption(Encoding.UTF8.GetBytes(data), key, DoOAEPPadding);
        }

        static public byte[] DecryptionToByteArray(byte[] data, PrivateKey key, bool DoOAEPPadding)
        {
            try
            {
                byte[] decryptedData = null;
                using (RSACryptoServiceProvider rsa = new RSACryptoServiceProvider())
                {
                    rsa.ImportParameters(key);
                    decryptedData = rsa.Decrypt(data, DoOAEPPadding);
                }

                return decryptedData;
            }
            catch (CryptographicException e)
            {
                Debug.Log(e.ToString());
            }

            return null;
        }

        static public string DecryptionToString(byte[] data, PrivateKey key, bool DoOAEPPadding)
        {
            byte[] decrypted = DecryptionToByteArray(data, key, DoOAEPPadding);
            return Encoding.UTF8.GetString(decrypted, 0, decrypted.Length);
        }
    }
}
