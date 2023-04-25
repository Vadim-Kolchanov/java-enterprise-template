CREATE TABLE push_notification_topic
(
    id          UUID PRIMARY KEY,
    code        VARCHAR UNIQUE NOT NULL,
    name        VARCHAR UNIQUE NOT NULL,
    description VARCHAR        NOT NULL
);

COMMENT ON TABLE push_notification_topic IS 'Push notification topic';
COMMENT ON COLUMN push_notification_topic.id IS 'Topic identifier';
COMMENT ON COLUMN push_notification_topic.code IS 'Topic code';
COMMENT ON COLUMN push_notification_topic.name IS 'Topic name';
COMMENT ON COLUMN push_notification_topic.description IS 'Description';

INSERT INTO push_notification_topic
VALUES ('9a74e24d-8c0d-4c27-b22e-58622d6597b6', 'ALL', 'all', 'All mobile devices'),
       ('462c5f18-c076-452d-8c3d-9e75533bb4fc', 'ANDROID', 'android', 'Mobile device by android'),
       ('827d52b7-571b-4019-ad26-8416c71eb59f', 'IOS', 'ios', 'Mobile device by ios');