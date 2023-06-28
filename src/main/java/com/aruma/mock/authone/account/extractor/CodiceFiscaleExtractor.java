package com.aruma.mock.authone.account.extractor;

import com.aruma.mock.authone.account.exception.CodiceFiscaleException;
import com.aruma.mock.authone.account.exception.FileException;
import com.aruma.mock.authone.core.utils.StringUtils;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cms.*;
import org.bouncycastle.util.Store;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Iterator;

@Slf4j
public class CodiceFiscaleExtractor {

    private static byte[] readFile(String filePath) throws IOException {
        log.info("Reading file: {}", filePath);
        FileInputStream fis = new FileInputStream(filePath);
        byte[] data = new byte[fis.available()];
        fis.read(data);
        fis.close();
        return data;
    }

    public static String extractCodiceFiscaleFromP7M(byte[] p7mData) {
        log.info("Extracting Codice Fiscale from P7M file");

        CMSSignedData cmsSignedData;
        SignerInformation signerInfo;
        X509Certificate signerCertificate;
        String codiceFiscale = null;
        try {
            cmsSignedData = new CMSSignedData(p7mData);
            signerInfo = getSignerInformation(cmsSignedData);
            signerCertificate = getSignerCertificate(signerInfo, cmsSignedData);
            codiceFiscale = extractCodiceFiscaleFromCertificate(signerCertificate);
        } catch (GeneralSecurityException | CMSException e) {
            log.error("An error occurred while reading file content. Check file validity", e);
            throw new FileException("An error occurred while reading file content. Check file validity", "003");
        }
        //the method extract the fiscal code with also with what looks like the country reference, removing.
        if(codiceFiscale.contains("IT:")) codiceFiscale = codiceFiscale.substring(2);
        log.info("Extracted Codice Fiscale: {}", codiceFiscale);
        return codiceFiscale;
    }

    private static SignerInformation getSignerInformation(CMSSignedData cmsSignedData) throws CMSException {
        log.debug("Getting signer information");
        SignerInformation signerInfo = null;
        SignerInformationStore signerInfos = cmsSignedData.getSignerInfos();
        Collection<SignerInformation> signers = signerInfos.getSigners();
        Iterator<SignerInformation> iterator = signers.iterator();
        if (iterator.hasNext()) {
            signerInfo = iterator.next();
        }
        return signerInfo;
    }

    private static X509Certificate getSignerCertificate(SignerInformation signerInfo, CMSSignedData cmsSignedData) {
        log.debug("Getting signer's certificate");
        Store<X509CertificateHolder> certificates = cmsSignedData.getCertificates();
        Collection<X509CertificateHolder> certHolders = certificates.getMatches(signerInfo.getSID());
        Iterator<X509CertificateHolder> iterator = certHolders.iterator();
        if (iterator.hasNext()) {
            try {
                X509CertificateHolder certHolder = iterator.next();
                return new JcaX509CertificateConverter().getCertificate(certHolder);
            } catch (CertificateException e) {
                log.error("Error converting certificate", e);
            }
        }
        return null;
    }

    private static String extractCodiceFiscaleFromCertificate(X509Certificate certificate) throws CertificateEncodingException {
        log.debug("Extracting Codice Fiscale from certificate");
        String subjectDN = certificate.getSubjectDN().getName();
        String[] dnComponents = subjectDN.split(",");
        for (String dnComponent : dnComponents) {
            String[] keyValue = dnComponent.trim().split("=");
            if (keyValue.length == 2 && keyValue[0].equalsIgnoreCase("SERIALNUMBER")) {
                log.info("Codice Fiscale extracted from certificate: {}", keyValue[1]);
                return keyValue[1];
            }
        }
        log.warn("Codice Fiscale not found in the certificate");
        return null; // Codice Fiscale not found in the certificate
    }

    public static boolean verifyCodiceFiscale(String cfFile, String cfForm) {

        if (StringUtils.isNotNull(cfFile) && StringUtils.isNotNull(cfForm)) {
            if (cfFile.equals(cfForm)) return true;
            else return false;
        } else {
            String cf = cfFile == null ? "dal file" : " dalla input form";
            String message = "Codice fiscale estratto da " + cf + "é null!";
            return false;
            //  throw new CodiceFiscaleException(message, "003");
        }
    }

}