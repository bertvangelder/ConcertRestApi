CREATE TABLE Artist (
        artistId bigint(20) NOT NULL AUTO_INCREMENT,
        name VARCHAR(20) NOT NULL,
        PRIMARY KEY (artistId)
);

CREATE TABLE Venue (
        venueId bigint(20) NOT NULL AUTO_INCREMENT,
        name VARCHAR(20) NOT NULL,
        location VARCHAR(20),
        PRIMARY KEY (venueId)
);

CREATE TABLE Concert (
        concertId bigint(20) NOT NULL AUTO_INCREMENT,
        name VARCHAR(20) NOT NULL,
        artistId bigint(20) NOT NULL,
        venueId bigint(20) NOT NULL,
        date DATE NOT NULL,
        time TIME NOT NULL,
        PRIMARY KEY (concertId),
        CONSTRAINT artist_fk FOREIGN KEY(artistId) references Artist(artistId),
        CONSTRAINT venue_fk FOREIGN KEY(venueId) references Venue(venueId)
);
