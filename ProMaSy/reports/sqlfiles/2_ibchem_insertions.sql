-- Specific Insertions

SET SCHEMA 'promasy';


INSERT INTO institute
(inst_name)
VALUES
  ('Інститут біохімії ім. О.В. Палладіна Національної академії наук України');

INSERT INTO departments
(dep_name, inst_id)
VALUES
  ('Дирекція', (SELECT inst_id
                FROM institute
                WHERE inst_name = 'Інститут біохімії ім. О.В. Палладіна Національної академії наук України')),
  ('Бухгалтерія', (SELECT inst_id
                   FROM institute
                   WHERE inst_name = 'Інститут біохімії ім. О.В. Палладіна Національної академії наук України')),
  ('Відділ постачання', (SELECT inst_id
                         FROM institute
                         WHERE inst_name = 'Інститут біохімії ім. О.В. Палладіна Національної академії наук України')),
  ('Відділ молекулярної імунології', (SELECT inst_id
                                      FROM institute
                                      WHERE inst_name =
                                            'Інститут біохімії ім. О.В. Палладіна Національної академії наук України')),
  ('Відділ біохімії м`язів', (SELECT inst_id
                              FROM institute
                              WHERE
                                inst_name = 'Інститут біохімії ім. О.В. Палладіна Національної академії наук України')),
  ('Відділ структури та функції білка', (SELECT inst_id
                                         FROM institute
                                         WHERE inst_name =
                                               'Інститут біохімії ім. О.В. Палладіна Національної академії наук України')),
  ('Відділ регуляції обміну речовин', (SELECT inst_id
                                       FROM institute
                                       WHERE inst_name =
                                             'Інститут біохімії ім. О.В. Палладіна Національної академії наук України')),
  ('Відділ біохімії вітамінів і коензимів', (SELECT inst_id
                                             FROM institute
                                             WHERE inst_name =
                                                   'Інститут біохімії ім. О.В. Палладіна Національної академії наук України')),
  ('Відділ біохімії ліпідів', (SELECT inst_id
                               FROM institute
                               WHERE inst_name =
                                     'Інститут біохімії ім. О.В. Палладіна Національної академії наук України')),
  ('Відділ хімії та біохімії ферментів', (SELECT inst_id
                                          FROM institute
                                          WHERE inst_name =
                                                'Інститут біохімії ім. О.В. Палладіна Національної академії наук України')),
  ('Відділ нейрохімії', (SELECT inst_id
                         FROM institute
                         WHERE inst_name = 'Інститут біохімії ім. О.В. Палладіна Національної академії наук України')),
  ('Відділ молекулярної біології', (SELECT inst_id
                                    FROM institute
                                    WHERE inst_name =
                                          'Інститут біохімії ім. О.В. Палладіна Національної академії наук України')),
  ('Відділ науково-технічної інформації', (SELECT inst_id
                                           FROM institute
                                           WHERE inst_name =
                                                 'Інститут біохімії ім. О.В. Палладіна Національної академії наук України')),
  ('Лабораторія сигнальних механізмів клітини', (SELECT inst_id
                                                 FROM institute
                                                 WHERE inst_name =
                                                       'Інститут біохімії ім. О.В. Палладіна Національної академії наук України')),
  ('Експлуатаційно-технічний відділ', (SELECT inst_id
                                       FROM institute
                                       WHERE inst_name =
                                             'Інститут біохімії ім. О.В. Палладіна Національної академії наук України')),
  ('Відділ техніки безпеки', (SELECT inst_id
                              FROM institute
                              WHERE
                                inst_name = 'Інститут біохімії ім. О.В. Палладіна Національної академії наук України')),
  ('Адміністративно-господарський відділ', (SELECT inst_id
                                            FROM institute
                                            WHERE inst_name =
                                                  'Інститут біохімії ім. О.В. Палладіна Національної академії наук України')),
  ('Бібліотека', (SELECT inst_id
                  FROM institute
                  WHERE inst_name = 'Інститут біохімії ім. О.В. Палладіна Національної академії наук України'));

INSERT INTO subdepartments
(subdep_name, dep_id)
VALUES
  ('Лабораторія імунобіології', (SELECT dep_id
                                 FROM departments
                                 WHERE dep_name = 'Відділ молекулярної імунології')),
  ('Лабораторія імунології клітинних рецепторів', (SELECT dep_id
                                                   FROM departments
                                                   WHERE dep_name = 'Відділ молекулярної імунології')),
  ('Лабораторія нанобіотехнології', (SELECT dep_id
                                     FROM departments
                                     WHERE dep_name = 'Відділ молекулярної імунології')),
  ('Лабораторія медичної біохімії', (SELECT dep_id
                                     FROM departments
                                     WHERE dep_name = 'Відділ біохімії вітамінів і коензимів')),
  ('Лабораторія оптичних методів дослідження', (SELECT dep_id
                                                FROM departments
                                                WHERE dep_name = 'Відділ біохімії м`язів')),
  ('Група хроматографії', (SELECT dep_id
                           FROM departments
                           WHERE dep_name = 'Відділ біохімії ліпідів')),
  ('Група електронної мікроскопії', (SELECT dep_id
                                     FROM departments
                                     WHERE dep_name = 'Відділ хімії та біохімії ферментів'));
