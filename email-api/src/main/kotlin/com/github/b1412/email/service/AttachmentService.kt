package com.github.b1412.email.service

import arrow.core.Either
import com.github.b1412.api.service.BaseService
import com.github.b1412.aws.s3.UploadUtil
import com.github.b1412.email.dao.AttachmentDao
import com.github.b1412.email.entity.Attachment
import com.github.b1412.email.enum.AttachmentType
import com.github.b1412.extenstions.orElse
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile


@Service
class AttachmentService(
    val dao: AttachmentDao,
    val uploadUtil: UploadUtil
) : BaseService<Attachment, Long>(dao = dao) {
    fun createFile(type: AttachmentType?, tempFile: MultipartFile): Either<String, Attachment> {
        if (tempFile.isEmpty) {
            return Either.left("no file uploaded")
        }
        val name = uploadUtil.write(tempFile, "")
        val attachment = Attachment(
            type = type.orElse(AttachmentType.UPLOAD),
            name = name,
            contentType = tempFile.contentType!!,
            size = tempFile.size,
            originalFilename = tempFile.originalFilename!!,
            fullPath = "/v1/attachment/download?filename=$name"
        )
        save(attachment)
        return Either.right(attachment)
    }

}
