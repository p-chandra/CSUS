#lang racket

(define (Q0 x) (if(= x 0) Q1 Q0))
(define (Q1 x) (if(= x 1) Q2 P))
(define (Q2 x) Q2)
(define (P x) P)

(define (DFA-Acceptor x y z r)
  (if(null? x) ( if(equal? (member y (map eval z)) (map eval z))#t #f)
     (cond ((eq? y r) #f)
       (else (DFA-Acceptor (cdr x) (y (car x)) z r)))))
       
;test
;(DFA-Acceptor '(1 1 0 1 0) Q0 '(Q2) P)
;(DFA-Acceptor '(1 0 0 ) Q0 '(Q2) P)
