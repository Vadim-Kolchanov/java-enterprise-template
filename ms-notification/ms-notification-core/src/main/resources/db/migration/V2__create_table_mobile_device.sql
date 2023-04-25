CREATE TABLE mobile_device
(
    id                                     UUID PRIMARY KEY,
    device_id                              VARCHAR UNIQUE           NOT NULL,
    mobile_platform                        VARCHAR                  NOT NULL,
    firebase_registration_token            VARCHAR                  NOT NULL,
    firebase_registration_token_updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    created_at                             TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at                             TIMESTAMP WITH TIME ZONE NOT NULL
);

COMMENT ON TABLE mobile_device IS 'Mobile device';
COMMENT ON COLUMN mobile_device.id IS 'Identifier';
COMMENT ON COLUMN mobile_device.device_id IS 'Identifier of device';
COMMENT ON COLUMN mobile_device.mobile_platform IS 'Mobile platform (android, ios)';
COMMENT ON COLUMN mobile_device.firebase_registration_token IS 'Firebase registration token';
COMMENT ON COLUMN mobile_device.firebase_registration_token_updated_at IS 'Timestamp when firebase registration token was updated';
COMMENT ON COLUMN mobile_device.created_at IS 'Creation timestamp in our system';
COMMENT ON COLUMN mobile_device.updated_at IS 'Timestamp when data was updated';

CREATE TABLE mobile_device_push_notification_topic
(
    mobile_device_id           UUID NOT NULL REFERENCES mobile_device,
    push_notification_topic_id UUID NOT NULL REFERENCES push_notification_topic,
    PRIMARY KEY (mobile_device_id, push_notification_topic_id)
);

COMMENT ON TABLE mobile_device_push_notification_topic IS 'Table for many-to-many relationship between mobile_device and push_notification_topic';
COMMENT ON COLUMN mobile_device_push_notification_topic.mobile_device_id IS 'Mobile device identifier';
COMMENT ON COLUMN mobile_device_push_notification_topic.push_notification_topic_id IS 'Push notification topic identifier';