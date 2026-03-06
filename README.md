# Password Manager (JavaFX)

A simple desktop password manager written in Java.
The application stores credentials securely using strong cryptography and provides a graphical interface built with JavaFX.

## Features

* Master password authentication
* Encrypted vault storage
* Add and delete password entries
* Secure password generator
* Reveal password on demand
* Copy password to clipboard
* Clipboard auto-clear after 30 seconds
* Simple graphical interface

## Example screen

Login screen

```
Password Manager

[ Master Password ]
[ Unlock Vault ]
```

Vault view

```
Site        Username        Password
--------------------------------------------
github      user123         •••••• [Reveal] [Copy]
gmail       mail@email      •••••• [Reveal] [Copy]

[ Add ]   [ Delete ]
```

## Project Structure

```
src/main/java/org/example

app
 └── PasswordManagerApp.java

model
 ├── PasswordEntry.java
 └── Vault.java

security
 ├── CryptoService.java
 ├── KeyDerivation.java
 └── PasswordGenerator.java

storage
 └── EncryptedVaultRepository.java

ui
 ├── VaultView.java
 └── AddPasswordDialog.java
```

## How It Works

The application stores all credentials in an encrypted vault file.

```
User Master Password
        ↓
PBKDF2 Key Derivation
        ↓
AES-GCM Encryption
        ↓
Encrypted vault.dat file
```

### Encryption

* Algorithm: AES-256 GCM
* Key derivation: PBKDF2WithHmacSHA256
* Random salt
* Random IV
* Authenticated encryption

The vault file contains:

```
[salt]
[IV]
[ciphertext]
```

Without the correct master password the vault cannot be decrypted.

## Password Generator

Passwords can be:

* manually entered
* automatically generated

The generator uses a secure random source.

Example generated password:

```
aF8$kP3!tZ1@qR7s
```

## Clipboard Security

When a password is copied:

1. It is placed in the system clipboard
2. After 30 seconds the clipboard is automatically cleared

This reduces the risk of password leakage.

## Running the Application

Requirements:

* Java 17+
* Maven

Run with:

```
mvn javafx:run
```

This launches the JavaFX interface.

## First Launch

On the first run the application creates a new encrypted vault file:

```
vault.dat
```

Any master password can be used to initialize the vault.

The same password must be used to unlock the vault later.

## Technologies Used

* Java 17
* JavaFX
* Maven
* Jackson JSON

## Security Notes

This project demonstrates the architecture of a password manager but should be considered a learning project.

For production use additional security measures would be required, such as:

* secure memory handling
* stronger password validation
* vault integrity verification
* UI protections against shoulder surfing

## Future Improvements

Possible enhancements:

* password search
* password strength meter
* editing entries
* automatic clipboard clearing timer UI
* export/import vault
* dark mode
* browser extension integration

## License

MIT License
