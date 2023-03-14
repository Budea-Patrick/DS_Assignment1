insert into artist (id, name) VALUES
    (1, 'Folk band'),
    (2, 'Pop artist'),
    (3, 'Techno DJ'),
    (4, 'Blues band'),
    (5, 'Rap artist');

insert into concerts (id, date, genre, title, tickets) VALUES
    (1, '2020-12-01 20:00:00', 'Blues', 'Midnight Blues', 20000),
    (2, '2020-12-01 22:00:00', 'Soul', 'Soul Song', 20000),
    (3, '2020-12-02 22:00:00', 'Techno', 'Techno Set', 20000),
    (4, '2020-12-03 20:00:00', 'Pop', 'Pop Mix', 20000),
    (5, '2020-12-03 22:00:00', 'Rap', 'Rap Battle', 20000);

insert into concerts_artists (concert_id, artists_id) VALUES
    (1, 4),
    (1, 1),
    (2, 1),
    (3, 3),
    (3, 5),
    (4, 2),
    (4, 3),
    (5, 5);