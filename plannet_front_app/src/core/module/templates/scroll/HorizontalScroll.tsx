import styles from './HorizontalScroll.module.css'
import { ReactNode, useRef, useState, useEffect } from 'react';
import * as React from "react";

interface HorizontalScrollProps<T> {
    items: T[];
    renderItem: (item: T) => ReactNode;
    keyExtractor: (item: T) => string | number;
    cardWidth?: number;
    gap?: number;
    className?: string;
}

const HorizontalScroll = <T,>(
    {
        items,
        renderItem,
        keyExtractor,
        cardWidth = 280,
        gap = 15,
        className = ''
    }: HorizontalScrollProps<T>) => {
    const [showArrows, setShowArrows] = useState({ left: false, right: false });
    const scrollRef = useRef<HTMLDivElement>(null);
    const containerRef = useRef<HTMLDivElement>(null);

    const checkScrollPosition = () => {
        if (scrollRef.current && containerRef.current) {
            const { scrollLeft, scrollWidth, clientWidth } = scrollRef.current;
            const containerWidth = containerRef.current.clientWidth;

            setShowArrows({
                left: scrollLeft > 0,
                right: scrollLeft < scrollWidth - clientWidth - 5 && scrollWidth > containerWidth
            });
        }
    };

    const scroll = (direction: 'left' | 'right') => {
        if (scrollRef.current) {
            const scrollAmount = direction === 'left' ? -cardWidth : cardWidth;
            scrollRef.current.scrollBy({
                left: scrollAmount,
                behavior: 'smooth'
            });
        }
    };

    useEffect(() => {
        const handleResize = () => checkScrollPosition();
        window.addEventListener('resize', handleResize);
        return () => window.removeEventListener('resize', handleResize);
    }, []);

    useEffect(() => {
        checkScrollPosition();
    }, [items]);

    return (
        <div
            ref={containerRef}
            className={`${styles.container} ${className}`}
            style={{ '--card-width': `${cardWidth}px`, '--gap': `${gap}px` } as React.CSSProperties}
        >
            {showArrows.left && (
                <button
                    className={`${styles.arrow} ${styles.leftArrow}`}
                    onClick={() => scroll('left')}
                    aria-label="Scroll left"
                >
                    &lt;
                </button>
            )}

            <div
                ref={scrollRef}
                className={styles.scrollContainer}
                onScroll={checkScrollPosition}
            >
                {items.map((item) => (
                    <div key={keyExtractor(item)} className={styles.cardWrapper}>
                        {renderItem(item)}
                    </div>
                ))}
            </div>

            {showArrows.right && (
                <button
                    className={`${styles.arrow} ${styles.rightArrow}`}
                    onClick={() => scroll('right')}
                    aria-label="Scroll right"
                >
                    &gt;
                </button>
            )}
        </div>
    );
}

export default HorizontalScroll;