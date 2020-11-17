;Pawan Chandra
;219493392
;CSC 135

#lang racket
(define (concatL x y)
(cond ((null? x) y)
      (else (cons(string-append(combine (car x))(combine (car y)))
             (concatL (cdr x) (cdr y)))))
)

(define (combine lst)
  (eval (cons string-append (map symbol->string lst))))

