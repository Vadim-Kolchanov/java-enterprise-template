CREATE TABLE push_notification
(
    id                          UUID PRIMARY KEY,
    firebase_registration_token VARCHAR,
    topic_name           VARCHAR,
    title                       VARCHAR                  NOT NULL,
    message                     VARCHAR                  NOT NULL,
    created_at                  TIMESTAMP WITH TIME ZONE NOT NULL
);

COMMENT ON TABLE push_notification IS 'Push notification';
COMMENT ON COLUMN push_notification.id IS 'Identifier';
COMMENT ON COLUMN push_notification.topic_name IS 'Push notification topic name';
COMMENT ON COLUMN push_notification.firebase_registration_token IS 'Firebase registration token';
COMMENT ON COLUMN push_notification.title IS 'Title';
COMMENT ON COLUMN push_notification.message IS 'Message';
COMMENT ON COLUMN push_notification.created_at IS 'Creation timestamp in our system';