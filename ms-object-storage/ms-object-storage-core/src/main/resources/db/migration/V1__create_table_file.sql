CREATE TABLE file
(
    id            UUID PRIMARY KEY,
    folder        VARCHAR                  NOT NULL,
    file_name     VARCHAR                  NOT NULL,
    uri           VARCHAR UNIQUE           NOT NULL,
    content_type  VARCHAR                  NOT NULL,
    size_in_bytes BIGINT                   NOT NULL,
    created_at    TIMESTAMP WITH TIME ZONE NOT NULL
);

COMMENT ON TABLE file IS 'File';
COMMENT ON COLUMN file.id IS 'File identifier';
COMMENT ON COLUMN file.folder IS 'Folder';
COMMENT ON COLUMN file.file_name IS 'File name';
COMMENT ON COLUMN file.uri IS 'File uri';
COMMENT ON COLUMN file.content_type IS 'File content type';
COMMENT ON COLUMN file.size_in_bytes IS 'Size in bytes';
COMMENT ON COLUMN file.created_at IS 'Creation timestamp';