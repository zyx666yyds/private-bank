CREATE TABLE `approval_flow` (
  `flow_id` varchar(36) NOT NULL COMMENT '审批流程ID，UUID格式',
  `trade_id` varchar(20) NOT NULL COMMENT '关联交易流水号',
  `current_level` int(11) NOT NULL COMMENT '当前审批等级：1-客户经理，2-风控，3-合规',
  `status` varchar(20) NOT NULL COMMENT '状态：审批中/已通过/已拒绝/已终止',
  `submit_time` datetime NOT NULL COMMENT '提交时间',
  `complete_time` datetime DEFAULT NULL COMMENT '完成时间',
  `creator_id` varchar(12) NOT NULL COMMENT '创建人ID',
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`flow_id`),
  UNIQUE KEY `idx_trade` (`trade_id`) COMMENT '交易ID唯一索引',
  KEY `idx_status` (`status`) COMMENT '状态索引',
  CONSTRAINT `approval_flow_ibfk_1` FOREIGN KEY (`trade_id`) REFERENCES `trade` (`trade_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='审批流程表'

CREATE TABLE `approval_operation_log` (
  `log_id` varchar(36) NOT NULL COMMENT '日志ID',
  `flow_id` varchar(36) NOT NULL COMMENT '关联审批流程ID',
  `operation_type` varchar(20) NOT NULL COMMENT '操作类型：提交/审批/驳回/重新提交/终止',
  `operator_id` varchar(12) NOT NULL COMMENT '操作人ID',
  `operation_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  `operation_comment` text COMMENT '操作备注',
  `previous_status` varchar(20) DEFAULT NULL COMMENT '操作前状态',
  `current_status` varchar(20) DEFAULT NULL COMMENT '操作后状态',
  PRIMARY KEY (`log_id`),
  KEY `idx_flow` (`flow_id`) COMMENT '流程ID索引',
  KEY `idx_operator` (`operator_id`) COMMENT '操作人索引',
  CONSTRAINT `approval_operation_log_ibfk_1` FOREIGN KEY (`flow_id`) REFERENCES `approval_flow` (`flow_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='审批操作日志表'

CREATE TABLE `approval_record` (
  `approval_id` varchar(36) NOT NULL COMMENT '审批记录ID，UUID格式',
  `flow_id` varchar(36) NOT NULL COMMENT '关联审批流程ID',
  `trade_id` varchar(20) NOT NULL COMMENT '关联交易流水号',
  `level` int(11) NOT NULL COMMENT '审批等级：1-客户经理审批，2-风控审批，3-合规审批',
  `approver_id` varchar(12) NOT NULL COMMENT '审批人编号',
  `decision` varchar(10) NOT NULL COMMENT '审批决定：通过/拒绝',
  `comment` text COMMENT '审批意见',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '审批时间',
  `is_deleted` tinyint(4) NOT NULL COMMENT '删除状态 （1表示已删除，0标识存在）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`approval_id`),
  KEY `idx_trade` (`trade_id`) COMMENT '交易ID索引',
  KEY `idx_approver` (`approver_id`) COMMENT '审批人ID索引',
  KEY `idx_level` (`level`) COMMENT '审批等级索引',
  KEY `approval_record_ibfk_2` (`flow_id`),
  CONSTRAINT `approval_record_ibfk_1` FOREIGN KEY (`trade_id`) REFERENCES `trade` (`trade_id`),
  CONSTRAINT `approval_record_ibfk_2` FOREIGN KEY (`flow_id`) REFERENCES `approval_flow` (`flow_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='交易审批记录表'

CREATE TABLE `approval_timeout_monitor` (
  `monitor_id` varchar(36) NOT NULL COMMENT '监控ID',
  `flow_id` varchar(36) NOT NULL COMMENT '关联审批流程ID',
  `current_level` int(11) NOT NULL COMMENT '当前审批等级',
  `expected_complete_time` datetime NOT NULL COMMENT '预期完成时间',
  `actual_complete_time` datetime DEFAULT NULL COMMENT '实际完成时间',
  `is_timeout` tinyint(1) DEFAULT '0' COMMENT '是否超时',
  `timeout_duration` int(11) DEFAULT NULL COMMENT '超时时长(分钟)',
  PRIMARY KEY (`monitor_id`),
  KEY `idx_flow_level` (`flow_id`,`current_level`) COMMENT '流程和等级联合索引',
  CONSTRAINT `approval_timeout_monitor_ibfk_1` FOREIGN KEY (`flow_id`) REFERENCES `approval_flow` (`flow_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='审批超时监控表'

CREATE TABLE `client` (
  `client_id` varchar(18) NOT NULL COMMENT '客户编号，18位纯数字，如100010000000123456',
  `name` varchar(50) NOT NULL COMMENT '客户姓名',
  `gender` char(1) NOT NULL COMMENT '性别，M表示男性，F表示女性',
  `birthday` date NOT NULL COMMENT '出生日期',
  `phone_number` varchar(20) NOT NULL COMMENT '手机号码，唯一标识',
  `email` varchar(100) DEFAULT NULL COMMENT '电子邮箱地址',
  `password` varchar(150) NOT NULL COMMENT '密码',
  `nationality` varchar(50) NOT NULL COMMENT '国籍',
  `id_type` varchar(20) NOT NULL COMMENT '证件类型：身份证/护照/其他',
  `id_number` varchar(30) NOT NULL COMMENT '证件号码',
  `income_level` varchar(30) NOT NULL COMMENT '年收入等级，如50-100万',
  `risk_level` varchar(20) NOT NULL COMMENT '风险等级：保守型/稳健型/进取型',
  `total_assets` decimal(20,2) DEFAULT '0.00' COMMENT '在本行资产总额(元)',
  `relationship_manager_id` varchar(12) NOT NULL COMMENT '所属客户经理编号，如U00012345678',
  `register_date` datetime NOT NULL COMMENT '客户创建时间',
  `kyc_due_date` date NOT NULL COMMENT '下次KYC评估到期日期',
  `status` varchar(10) NOT NULL COMMENT '客户状态：正常/冻结/注销',
  `remarks` text COMMENT '附加备注信息，如客户偏好等',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间',
  `is_deleted` tinyint(4) NOT NULL COMMENT '删除状态 （1表示已删除，0标识存在）',
  PRIMARY KEY (`client_id`),
  KEY `idx_phone` (`phone_number`) COMMENT '手机号索引',
  KEY `idx_manager` (`relationship_manager_id`) COMMENT '客户经理ID索引',
  KEY `idx_status` (`status`) COMMENT '状态索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户基本信息表'

CREATE TABLE `notification` (
  `notification_id` varchar(32) NOT NULL COMMENT '通知ID，格式NOTIF-yyyyMMdd-nnnnnn或UUID',
  `client_id` varchar(18) NOT NULL COMMENT '接收人客户编号',
  `type` varchar(30) NOT NULL COMMENT '通知类型：交易状态/审批结果/KYC提醒等',
  `content` text NOT NULL COMMENT '通知正文内容',
  `read` tinyint(1) DEFAULT '0' COMMENT '是否已读',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '通知创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '通知更新时间',
  `is_deleted` tinyint(4) NOT NULL COMMENT '删除状态 （1表示已删除，0标识存在）',
  PRIMARY KEY (`notification_id`),
  KEY `idx_client` (`client_id`) COMMENT '客户ID索引',
  KEY `idx_read` (`read`) COMMENT '已读状态索引',
  KEY `idx_created` (`created_at`) COMMENT '创建时间索引',
  CONSTRAINT `notification_ibfk_1` FOREIGN KEY (`client_id`) REFERENCES `client` (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统通知表'

CREATE TABLE `portfolio` (
  `portfolio_id` varchar(20) NOT NULL COMMENT '组合编号，格式PFyyyyMMddnnnn，如PF202503230001',
  `client_id` varchar(18) NOT NULL COMMENT '所属客户编（外键）',
  `name` varchar(50) NOT NULL COMMENT '组合名称',
  `total_value` decimal(20,2) DEFAULT '0.00' COMMENT '组合总估值(元)',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '组合创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '组合更新时间',
  `is_deleted` tinyint(4) NOT NULL COMMENT '删除状态 （1表示已删除，0标识存在）',
  PRIMARY KEY (`portfolio_id`),
  KEY `idx_client` (`client_id`) COMMENT '客户ID索引',
  CONSTRAINT `portfolio_ibfk_1` FOREIGN KEY (`client_id`) REFERENCES `client` (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户投资组合主表'

CREATE TABLE `portfolio_item` (
  `item_id` varchar(32) NOT NULL COMMENT '持仓项ID，短UUID格式',
  `portfolio_id` varchar(20) NOT NULL COMMENT '所属组合ID （外键）',
  `product_code` varchar(20) NOT NULL COMMENT '产品编码，如FD-00012',
  `product_name` varchar(100) NOT NULL COMMENT '产品名称',
  `type` varchar(20) NOT NULL COMMENT '产品类型：基金/理财/股票',
  `amount` decimal(20,2) NOT NULL COMMENT '持有金额或数量',
  `unit_value` decimal(20,4) NOT NULL COMMENT '单位净值',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updated_at` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint(4) NOT NULL COMMENT '删除状态 （1表示已删除，0标识存在）',
  PRIMARY KEY (`item_id`),
  KEY `idx_portfolio` (`portfolio_id`) COMMENT '组合ID索引',
  KEY `idx_product` (`product_code`) COMMENT '产品编码索引',
  CONSTRAINT `portfolio_item_ibfk_1` FOREIGN KEY (`portfolio_id`) REFERENCES `portfolio` (`portfolio_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='投资组合持仓明细表'

CREATE TABLE `recommendation` (
  `recommendation_id` varchar(40) NOT NULL COMMENT '推荐ID，格式RE-clientId-UUID缩写，如RE-10000000001-89f2',
  `client_id` varchar(18) NOT NULL COMMENT '目标客户编号 （外键）',
  `advisor_id` varchar(12) NOT NULL COMMENT '推荐客户经理编号',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '推荐创建时间',
  `accepted` tinyint(1) DEFAULT '0' COMMENT '是否被客户采纳',
  `feedback` text COMMENT '客户反馈内容',
  `updated_at` datetime DEFAULT NULL COMMENT '推荐更新时间',
  `is_deleted` tinyint(4) NOT NULL COMMENT '删除状态 （1表示已删除，0标识存在）',
  PRIMARY KEY (`recommendation_id`),
  KEY `idx_client` (`client_id`) COMMENT '客户ID索引',
  KEY `idx_advisor` (`advisor_id`) COMMENT '客户经理ID索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='投资顾问推荐记录表'

CREATE TABLE `recommended_item` (
  `item_id` varchar(36) NOT NULL COMMENT '推荐项ID，UUID格式',
  `recommendation_id` varchar(40) NOT NULL COMMENT '所属推荐记录ID （外键）',
  `product_code` varchar(20) NOT NULL COMMENT '推荐产品编码',
  `amount` decimal(20,2) NOT NULL COMMENT '推荐金额(元)',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint(4) NOT NULL COMMENT '删除状态 （1表示已删除，0标识存在）',
  PRIMARY KEY (`item_id`),
  KEY `idx_recommendation` (`recommendation_id`) COMMENT '推荐记录ID索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='推荐产品明细表'

CREATE TABLE `risk_assessment` (
  `risk_id` varchar(32) NOT NULL COMMENT '风控评估编号，格式RISK-yyyyMMdd-clientId，如RISK-20250323-100000001',
  `client_id` varchar(18) NOT NULL COMMENT '客户编号',
  `evaluator_id` varchar(12) NOT NULL COMMENT '风控人员编号',
  `score` int(11) NOT NULL COMMENT '风险得分，0-100分',
  `result_level` varchar(20) NOT NULL COMMENT '评估结果等级：保守型/稳健型/进取型',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '评估时间',
  `remarks` text COMMENT '附注说明',
  `updated_at` datetime DEFAULT NULL COMMENT '更新评估时间',
  `is_deleted` tinyint(4) NOT NULL COMMENT '删除状态 （1表示已删除，0标识存在）',
  PRIMARY KEY (`risk_id`),
  KEY `idx_client` (`client_id`) COMMENT '客户ID索引',
  KEY `idx_evaluator` (`evaluator_id`) COMMENT '评估人ID索引',
  KEY `idx_client_created` (`client_id`,`created_at`),
  CONSTRAINT `risk_assessment_ibfk_1` FOREIGN KEY (`client_id`) REFERENCES `client` (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户风险评估表'
  
CREATE TABLE `trade` (
  `trade_id` varchar(20) NOT NULL COMMENT '交易流水号，格式TyyyyMMddnnnnnn，如T2025032300123',
  `client_id` varchar(18) NOT NULL COMMENT '客户编号',
  `product_code` varchar(20) DEFAULT NULL COMMENT '产品编号，调仓交易可能为空',
  `type` varchar(20) NOT NULL COMMENT '交易类型：申购/赎回/调仓',
  `amount` decimal(20,2) NOT NULL COMMENT '交易金额或数量',
  `status` varchar(20) NOT NULL COMMENT '交易状态：待审批/审批中/已拒绝/已执行/失败',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '交易创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '交易状态更新时间',
  `is_deleted` tinyint(4) NOT NULL COMMENT '删除状态 （1表示已删除，0标识存在）',
  PRIMARY KEY (`trade_id`),
  KEY `idx_client` (`client_id`) COMMENT '客户ID索引',
  KEY `idx_status` (`status`) COMMENT '状态索引',
  KEY `idx_create_time` (`create_time`) COMMENT '创建时间索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='交易记录表'


CREATE TABLE `user` (
  `user_id` varchar(12) NOT NULL COMMENT '用户ID，12位，前缀u，如U00012345678',
  `username` varchar(50) NOT NULL COMMENT '登录用户名，唯一',
  `password` varchar(100) NOT NULL COMMENT '加密后的密码',
  `role` varchar(30) NOT NULL COMMENT '用户角色：客户/客户经理/风控人员/合规人员/管理员',
  `status` varchar(10) NOT NULL COMMENT '状态：正常/禁用/锁定',
  `last_login` datetime DEFAULT NULL COMMENT '最后登录时间',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(4) NOT NULL COMMENT '删除状态 （1表示已删除，0标识存在）',
  PRIMARY KEY (`user_id`),
  KEY `idx_username` (`username`) COMMENT '用户名索引',
  KEY `idx_role` (`role`) COMMENT '角色索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统用户表'
