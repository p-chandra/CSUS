;Pawan Chandra
;219493392

#lang racket
(define(buildList x A y B)(appendList(buildLeft x A) (buildRight y B)))

  (define(buildLeft x A)
  (if (= x 0)'()(cons A(buildLeft (- x 1) A))))

  (define(buildRight y B)
  (if (= y 0)'()(cons B(buildRight (- y 1) B))))

(define (appendList A B)
  (if (null? A) B
      (cons(car A) (appendList (cdr A) B))))
