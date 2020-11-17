#lang racket

(define (selectN x)
  (lambda(list)
    (define(iter l i)
      (if(= i 0)l(iter (reverse (cdr (reverse l)))(- i 1))))
                      (iter list x)))

(define last (selectN 3))
(define result (last '(4 8 2 9 -1 13)))
(display result)



;Damnwolfson + Joel for the Code
;(define (selectN x) (lambda (lst)(reverse (removeN x(reverse lst)))))
;(define (removeN x y) (if (> x 0) (removeN (- x 1)(cdr y)) y))
;(define (First x) ((selectN 3) x))
