package com.chinasoft.approvalservice.utils;

import com.docusign.esign.api.EnvelopesApi;
import com.docusign.esign.client.ApiClient;
import com.docusign.esign.model.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

public class DocuSignService {

    private final EnvelopesApi envelopesApi;
    private final String accountId;

    public DocuSignService(ApiClient apiClient, String accountId) {
        this.envelopesApi = new EnvelopesApi(apiClient);
        this.accountId = accountId;
    }

    /**
     * 发出签名请求
     * @param pdfFilePath
     * @param emailSubject
     * @param signerName
     * @param signerEmail
     * @param signPageNumber
     * @param xPosition
     * @param yPosition
     * @return
     * @throws Exception
     */
    public String sendSignatureRequest(
            String pdfFilePath,
            String emailSubject,
            String signerName,
            String signerEmail,
            int signPageNumber,
            int xPosition,
            int yPosition) throws Exception {

        // 1. 编码PDF文件
        byte[] fileBytes = Files.readAllBytes(Paths.get(pdfFilePath));
        String documentBase64 = Base64.getEncoder().encodeToString(fileBytes);

        // 2. 创建文档对象
        Document document = new Document();
        document.setDocumentBase64(documentBase64);
        document.setName("contract.pdf");
        document.setFileExtension("pdf");
        document.setDocumentId("1");


        // 3. 设置签名位置
        SignHere signHere = new SignHere();
        signHere.setDocumentId("1");
        signHere.setPageNumber(String.valueOf(signPageNumber));
        signHere.setXPosition(String.valueOf(xPosition));
        signHere.setYPosition(String.valueOf(yPosition));


        // 4. 配置签署人
        Signer signer = new Signer();
        signer.setEmail(signerEmail);
        signer.setName(signerName);
        signer.setRecipientId("1");
//        signer.setTabs(new Tabs().setSignHereTabs(Arrays.asList(signHere)));
//                .setEmail(signerEmail)
//                .setName(signerName)
//                .setRecipientId("1")
//                .setTabs(new Tabs().setSignHereTabs(List.of(signHere)));

        // 5. 创建信封
        EnvelopeDefinition envelope = new EnvelopeDefinition();
        envelope.setEmailSubject(emailSubject);
        envelope.setDocuments((List<Document>) document);
//        envelope.setRecipients(new Recipients().setSigners(Arrays.asList(signer)));
        envelope.setStatus("sent");
//                .setEmailSubject(emailSubject)
//                .setDocuments(List.of(document))
//                .setRecipients(new Recipients().setSigners(List.of(signer)))
//                .setStatus("sent"); // 立即发送

        // 6. 调用API
        EnvelopeSummary result = envelopesApi.createEnvelope(accountId, envelope);
        return result.getEnvelopeId();
    }

    /**
     * 查询签署状态
     * @param envelopeId
     * @return
     * @throws Exception
     */
    public String checkEnvelopeStatus(String envelopeId) throws Exception {
        Envelope envelope = envelopesApi.getEnvelope(accountId, envelopeId);
        return envelope.getStatus(); // "sent", "delivered", "completed", etc.
    }

    /**
     * 下载签署后的文档
     * @param envelopeId
     * @param outputPath
     * @throws Exception
     */
    public void downloadSignedDocument(String envelopeId, String outputPath) throws Exception {
        byte[] pdfBytes = envelopesApi.getDocument(accountId, envelopeId, "combined");
        Files.write(Paths.get(outputPath), pdfBytes);
    }
}