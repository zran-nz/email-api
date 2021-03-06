package com.github.b1412.email.dao

import com.github.b1412.api.dao.BaseDao
import com.github.b1412.email.entity.EmailServer
import org.springframework.stereotype.Repository

@Repository
interface EmailServerDao : BaseDao<EmailServer, Long>
