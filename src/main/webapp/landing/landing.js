document.addEventListener('DOMContentLoaded', () => {
    const desafiosSlider = document.getElementById('desafiosSlider');
    if (desafiosSlider) {
        const desafioSlides = Array.from(desafiosSlider.querySelectorAll('.desafio-slide'));
        const reduceDesafioMotion = window.matchMedia('(prefers-reduced-motion: reduce)');
        let activeDesafioIndex = desafioSlides.findIndex((slide) => slide.classList.contains('is-active'));
        let desafioRotationTimer;
        let desafioRotationPaused = false;
        let desafioPointerInside = false;
        let desafioFocusWithin = false;
        let desafioTransitionInProgress = false;
        const desafioPageButtons = Array.from(desafiosSlider.querySelectorAll('[data-desafio-indice]'));
        const desafioNavButtons = Array.from(desafiosSlider.querySelectorAll('[data-desafio-nav], [data-desafio-indice]'));

        const updateDesafioPagination = (index) => {
            desafioPageButtons.forEach((button, buttonIndex) => {
                const selected = buttonIndex === index;
                button.classList.toggle('is-active', selected);
                button.setAttribute('aria-pressed', String(selected));
            });
        };

        if (activeDesafioIndex < 0) {
            activeDesafioIndex = 0;
        }
        updateDesafioPagination(activeDesafioIndex);

        const showDesafio = (index, direction = 1) => {
            if (desafioTransitionInProgress || desafioSlides.length < 2) {
                return;
            }

            const nextIndex = (index + desafioSlides.length) % desafioSlides.length;
            if (nextIndex === activeDesafioIndex) {
                return;
            }

            const outgoingSlide = desafioSlides[activeDesafioIndex];
            const incomingSlide = desafioSlides[nextIndex];
            const travelDirection = direction < 0 ? -1 : 1;
            activeDesafioIndex = nextIndex;
            updateDesafioPagination(nextIndex);

            incomingSlide.classList.add('is-active');
            incomingSlide.setAttribute('aria-hidden', 'false');
            outgoingSlide.setAttribute('aria-hidden', 'true');

            if (reduceDesafioMotion.matches) {
                outgoingSlide.classList.remove('is-active');
                return;
            }

            const outgoingImage = outgoingSlide.querySelector('img');
            const incomingImage = incomingSlide.querySelector('img');
            const outgoingContent = outgoingSlide.querySelector('.desafio-conteudo');
            const incomingContent = incomingSlide.querySelector('.desafio-conteudo');
            const canAnimate = outgoingImage?.animate && incomingImage?.animate;

            if (!canAnimate) {
                outgoingSlide.classList.remove('is-active');
                return;
            }

            desafioTransitionInProgress = true;
            desafiosSlider.setAttribute('aria-busy', 'true');
            desafioNavButtons.forEach((button) => { button.disabled = true; });
            outgoingSlide.style.zIndex = '2';
            incomingSlide.style.zIndex = '1';
            outgoingSlide.style.pointerEvents = 'none';
            incomingSlide.style.pointerEvents = 'none';

            const duration = 850;
            const easing = 'cubic-bezier(0.22, 0.68, 0, 1)';
            const options = { duration, easing, fill: 'both' };
            const animations = [
                outgoingImage.animate([
                    { transform: 'translate3d(0, 0, 0) scale(1) rotate(0deg)', opacity: 1, clipPath: 'polygon(0% 0%, 100% 0%, 100% 100%, 0% 100%, 0% 100%, 0% 0%)' },
                    { offset: 0.48, transform: `translate3d(${-travelDirection * 28}px, 22px, 0) scale(1.02) rotate(${-travelDirection * 1.5}deg)`, opacity: 0.92, clipPath: 'polygon(0% 0%, 100% 0%, 100% 100%, 12% 100%, 0% 100%, 0% 0%)' },
                    { transform: `translate3d(${-travelDirection * 140}px, 76px, 0) scale(0.88) rotate(${-travelDirection * 4}deg)`, opacity: 0, clipPath: 'polygon(18% 0, 100% 0, 100% 84%, 82% 100%, 0 100%, 0 18%)' }
                ], options),
                incomingImage.animate([
                    { transform: `translate3d(${travelDirection * 132}px, -56px, 0) scale(0.84) rotate(${travelDirection * 4}deg)`, opacity: 0, clipPath: 'polygon(14% 0, 100% 0, 100% 82%, 86% 100%, 0 100%, 0 18%)' },
                    { offset: 0.55, transform: `translate3d(${travelDirection * 44}px, -18px, 0) scale(0.94) rotate(${travelDirection}deg)`, opacity: 0.86, clipPath: 'polygon(8% 0, 100% 0, 100% 91%, 91% 100%, 0 100%, 0 9%)' },
                    { transform: 'translate3d(0, 0, 0) scale(1) rotate(0deg)', opacity: 1, clipPath: 'polygon(0% 0%, 100% 0%, 100% 100%, 100% 100%, 0% 100%, 0% 0%)' }
                ], options)
            ];

            if (outgoingContent?.animate && incomingContent?.animate) {
                animations.push(outgoingContent.animate([
                    { transform: 'translate3d(0, 0, 0)', opacity: 1 },
                    { transform: `translate3d(${-travelDirection * 24}px, 10px, 0)`, opacity: 0 }
                ], { duration: 500, easing, fill: 'both' }));
                animations.push(incomingContent.animate([
                    { transform: `translate3d(${travelDirection * 22}px, -8px, 0)`, opacity: 0 },
                    { transform: 'translate3d(0, 0, 0)', opacity: 1 }
                ], { duration: 620, delay: 120, easing, fill: 'both' }));
            }

            Promise.all(animations.map((animation) => animation.finished.catch(() => undefined))).then(() => {
                outgoingSlide.classList.remove('is-active');
                outgoingSlide.style.zIndex = '';
                incomingSlide.style.zIndex = '';
                outgoingSlide.style.pointerEvents = '';
                incomingSlide.style.pointerEvents = '';
                animations.forEach((animation) => animation.cancel());
                desafioTransitionInProgress = false;
                desafiosSlider.removeAttribute('aria-busy');
                desafioNavButtons.forEach((button) => { button.disabled = false; });
            });
        };

        const scheduleDesafioRotation = () => {
            window.clearTimeout(desafioRotationTimer);
            if (desafioSlides.length < 2 || desafioRotationPaused || reduceDesafioMotion.matches || document.hidden) {
                return;
            }

            desafioRotationTimer = window.setTimeout(() => {
                showDesafio(activeDesafioIndex + 1, 1);
                scheduleDesafioRotation();
            }, 6500);
        };

        desafiosSlider.addEventListener('keydown', (event) => {
            if (event.key === 'ArrowRight' || event.key === 'ArrowLeft') {
                event.preventDefault();
                const direction = event.key === 'ArrowRight' ? 1 : -1;
                showDesafio(activeDesafioIndex + direction, direction);
                scheduleDesafioRotation();
            }
        });

        desafioNavButtons.forEach((button) => {
            button.addEventListener('click', () => {
                const targetIndex = Number(button.dataset.desafioIndice);
                const isPageButton = Number.isInteger(targetIndex);
                const direction = isPageButton
                    ? (targetIndex < activeDesafioIndex ? -1 : 1)
                    : (button.dataset.desafioNav === 'anterior' ? -1 : 1);
                showDesafio(isPageButton ? targetIndex : activeDesafioIndex + direction, direction);
                scheduleDesafioRotation();
            });
        });

        desafiosSlider.addEventListener('pointerenter', () => {
            desafioPointerInside = true;
            desafioRotationPaused = true;
            window.clearTimeout(desafioRotationTimer);
        });
        desafiosSlider.addEventListener('pointerleave', () => {
            desafioPointerInside = false;
            desafioRotationPaused = desafioFocusWithin;
            scheduleDesafioRotation();
        });
        desafiosSlider.addEventListener('focusin', () => {
            desafioFocusWithin = true;
            desafioRotationPaused = true;
            window.clearTimeout(desafioRotationTimer);
        });
        desafiosSlider.addEventListener('focusout', (event) => {
            if (!desafiosSlider.contains(event.relatedTarget)) {
                desafioFocusWithin = false;
                desafioRotationPaused = desafioPointerInside;
                scheduleDesafioRotation();
            }
        });
        document.addEventListener('visibilitychange', scheduleDesafioRotation);
        reduceDesafioMotion.addEventListener?.('change', scheduleDesafioRotation);
        scheduleDesafioRotation();
    }

    const header = document.getElementById('header');
    const mobileMenu = document.getElementById('menuMobile');
    const navigation = document.getElementById('navegacao');

    if (mobileMenu && navigation) {
        const closeMenu = () => {
            navigation.classList.remove('mobile-aberto');
            mobileMenu.setAttribute('aria-expanded', 'false');
            mobileMenu.setAttribute('aria-label', 'Abrir menu');
        };

        mobileMenu.addEventListener('click', () => {
            const isOpen = mobileMenu.getAttribute('aria-expanded') === 'true';
            navigation.classList.toggle('mobile-aberto', !isOpen);
            mobileMenu.setAttribute('aria-expanded', String(!isOpen));
            mobileMenu.setAttribute('aria-label', isOpen ? 'Abrir menu' : 'Fechar menu');
        });

        navigation.querySelectorAll('a').forEach((link) => link.addEventListener('click', closeMenu));
        document.addEventListener('click', (event) => {
            if (window.innerWidth <= 760 && header && !header.contains(event.target)) {
                closeMenu();
            }
        });
    }

    const faqButtons = Array.from(document.querySelectorAll('.faq-pergunta'));
    const faqCloseTimers = new WeakMap();
    const faqMotionDuration = window.matchMedia('(prefers-reduced-motion: reduce)').matches ? 0 : 240;

    const setFaqOpen = (button, open) => {
        const answer = document.getElementById(button.getAttribute('aria-controls'));
        if (!answer) {
            return;
        }

        window.clearTimeout(faqCloseTimers.get(answer));
        button.setAttribute('aria-expanded', String(open));

        if (open) {
            answer.hidden = false;
            answer.classList.remove('is-open');
            void answer.offsetHeight;
            window.requestAnimationFrame(() => {
                if (button.getAttribute('aria-expanded') === 'true') {
                    answer.classList.add('is-open');
                }
            });
            return;
        }

        answer.classList.remove('is-open');
        if (faqMotionDuration === 0) {
            answer.hidden = true;
            return;
        }

        const closeTimer = window.setTimeout(() => {
            if (button.getAttribute('aria-expanded') === 'false') {
                answer.hidden = true;
            }
        }, faqMotionDuration);
        faqCloseTimers.set(answer, closeTimer);
    };

    faqButtons.forEach((button) => {
        button.addEventListener('click', () => {
            const isOpen = button.getAttribute('aria-expanded') === 'true';
            faqButtons.forEach((otherButton) => {
                if (otherButton !== button && otherButton.getAttribute('aria-expanded') === 'true') {
                    setFaqOpen(otherButton, false);
                }
            });
            setFaqOpen(button, !isOpen);
        });
    });

    const profileButtons = document.querySelectorAll('.perfil-opcao');
    const profileFields = document.querySelectorAll('.funcionamento-passos [data-empresa][data-integrado]');
    const stepButtons = document.querySelectorAll('.passo-botao');
    const funcionamentoImage = document.getElementById('funcionamentoImagem');
    const funcionamentoVisual = document.getElementById('funcionamentoVisual');
    const funcionamentoViews = {
        empresa: [
            { src: '../assets/funcionamento-notebook-figma.png', alt: 'Painel da plataforma exibido em um notebook' },
            { duplo: true, alt: 'Dois celulares com as telas de acompanhamento da plataforma' },
            { duplo: true, alt: 'Dois celulares com as telas de acompanhamento da plataforma' }
        ],
        integrado: [
            { src: '../assets/funcionamento-integrado-figma.png', alt: 'Tela de cadastro da plataforma em um celular' },
            { duplo: true, alt: 'Dois celulares com as telas de acompanhamento da plataforma' },
            { duplo: true, alt: 'Dois celulares com as telas de acompanhamento da plataforma' }
        ]
    };
    const funcionamentoDuplo = document.getElementById('funcionamentoDuplo');
    let selectedProfile = 'empresa';
    let selectedStep = 0;
    let imageTransitionTimer;

    const showFunctioningView = () => {
        const view = funcionamentoViews[selectedProfile]?.[selectedStep];
        if (!view || !funcionamentoImage || !funcionamentoVisual || !funcionamentoDuplo) {
            return;
        }

        window.clearTimeout(imageTransitionTimer);
        funcionamentoVisual.classList.add('trocando-imagem');
        imageTransitionTimer = window.setTimeout(() => {
            const mostrarDuplo = view.duplo === true;
            funcionamentoVisual.classList.toggle('mostrando-duplo', mostrarDuplo);
            funcionamentoImage.style.display = mostrarDuplo ? 'none' : 'block';
            funcionamentoDuplo.style.display = mostrarDuplo ? 'block' : 'none';

            if (!mostrarDuplo) {
                funcionamentoImage.src = view.src;
                funcionamentoImage.alt = view.alt;
            }

            funcionamentoDuplo.setAttribute('aria-label', view.alt);
            window.requestAnimationFrame(() => {
                window.requestAnimationFrame(() => funcionamentoVisual.classList.remove('trocando-imagem'));
            });
        }, 150);
    };

    const selectFunctioningView = (profile, step) => {
        selectedProfile = profile;
        selectedStep = step;

        profileButtons.forEach((button) => {
            const selected = button.dataset.perfil === profile;
            button.classList.toggle('selecionado', selected);
            button.setAttribute('aria-pressed', String(selected));
        });

        profileFields.forEach((field) => {
            const content = field.dataset[profile];
            if (content) {
                field.textContent = content;
            }
        });

        stepButtons.forEach((button, index) => {
            const selected = index === step;
            const item = button.closest('[data-passo]');
            item?.classList.toggle('passo-destaque', selected);
            button.setAttribute('aria-pressed', String(selected));
        });

        showFunctioningView();
    };

    profileButtons.forEach((button) => {
        button.addEventListener('click', () => selectFunctioningView(button.dataset.perfil, 0));
    });

    stepButtons.forEach((button, index) => {
        button.addEventListener('click', () => selectFunctioningView(selectedProfile, index));
    });

    const featureSlider = document.querySelector('[data-slider="funcionalidades"]');
    if (featureSlider) {
        let originalCards = [];
        let cycleWidth = 0;
        let pointerStart = 0;
        let scrollStart = 0;
        let dragging = false;
        let animationFrame = 0;
        let animationStartedAt = 0;
        let animationFrom = 0;
        let animationTo = 0;

        const initializeInfiniteSlider = () => {
            featureSlider.querySelectorAll('[data-loop-clone="true"]').forEach((clone) => clone.remove());
            originalCards = [...featureSlider.querySelectorAll(':scope > .funcionalidade-card')];
            if (originalCards.length < 2) {
                return;
            }

            const before = originalCards.map((card) => {
                const clone = card.cloneNode(true);
                clone.dataset.loopClone = 'true';
                clone.setAttribute('aria-hidden', 'true');
                return clone;
            });
            const after = originalCards.map((card) => {
                const clone = card.cloneNode(true);
                clone.dataset.loopClone = 'true';
                clone.setAttribute('aria-hidden', 'true');
                return clone;
            });

            featureSlider.prepend(...before);
            featureSlider.append(...after);
            const gap = parseFloat(getComputedStyle(featureSlider).columnGap) || 0;
            cycleWidth = originalCards.reduce((total, card) => total + card.getBoundingClientRect().width, gap * originalCards.length);
            featureSlider.scrollLeft = cycleWidth;
        };

        window.requestAnimationFrame(initializeInfiniteSlider);
        const normalizeInfiniteSlider = () => {
            if (!cycleWidth) {
                return;
            }

            if (featureSlider.scrollLeft < cycleWidth - 1) {
                featureSlider.scrollLeft += cycleWidth;
            } else if (featureSlider.scrollLeft >= cycleWidth * 2) {
                featureSlider.scrollLeft -= cycleWidth;
            }
        };

        const animateSlider = (time) => {
            if (!animationStartedAt) {
                animationStartedAt = time;
            }

            const progress = Math.min((time - animationStartedAt) / 850, 1);
            const easedProgress = progress * progress * (3 - 2 * progress);
            featureSlider.scrollLeft = animationFrom + (animationTo - animationFrom) * easedProgress;

            if (progress < 1) {
                animationFrame = window.requestAnimationFrame(animateSlider);
                return;
            }

            animationFrame = 0;
            animationStartedAt = 0;
            normalizeInfiniteSlider();
        };

        const slideBy = (distance) => {
            window.cancelAnimationFrame(animationFrame);
            animationStartedAt = 0;
            animationFrom = featureSlider.scrollLeft;
            animationTo = animationFrom + distance;
            animationFrame = window.requestAnimationFrame(animateSlider);
        };

        featureSlider.addEventListener('scroll', () => {
            if (!cycleWidth || dragging || animationFrame) {
                return;
            }
            normalizeInfiniteSlider();
        }, { passive: true });

        featureSlider.addEventListener('pointerdown', (event) => {
            if (event.pointerType === 'mouse' && event.button !== 0) {
                return;
            }

            window.cancelAnimationFrame(animationFrame);
            animationFrame = 0;
            animationStartedAt = 0;
            dragging = true;
            pointerStart = event.clientX;
            scrollStart = featureSlider.scrollLeft;
            featureSlider.classList.add('arrastando');
            featureSlider.setPointerCapture(event.pointerId);
        });

        featureSlider.addEventListener('pointermove', (event) => {
            if (dragging) {
                featureSlider.scrollLeft = scrollStart - (event.clientX - pointerStart) * 0.7;
            }
        });

        const stopDragging = () => {
            dragging = false;
            featureSlider.classList.remove('arrastando');
            normalizeInfiniteSlider();
        };
        featureSlider.addEventListener('pointerup', stopDragging);
        featureSlider.addEventListener('pointercancel', stopDragging);
        featureSlider.addEventListener('lostpointercapture', stopDragging);

        featureSlider.addEventListener('keydown', (event) => {
            if (event.key !== 'ArrowRight' && event.key !== 'ArrowLeft') {
                return;
            }

            event.preventDefault();
            const firstCard = originalCards[0];
            const gap = parseFloat(getComputedStyle(featureSlider).columnGap) || 20;
            const distance = firstCard ? firstCard.getBoundingClientRect().width + gap : featureSlider.clientWidth;
            slideBy(event.key === 'ArrowRight' ? distance : -distance);
        });

        document.addEventListener('click', (event) => {
            const arrow = event.target.closest('[data-slider-direcao]');
            if (!arrow) {
                return;
            }

            const firstCard = originalCards[0];
            const gap = parseFloat(getComputedStyle(featureSlider).columnGap) || 20;
            const distance = firstCard ? firstCard.getBoundingClientRect().width + gap : featureSlider.clientWidth;
            const direction = arrow.dataset.sliderDirecao === 'anterior' ? -1 : 1;
            slideBy(distance * direction);
        });

        let resizeTimer;
        window.addEventListener('resize', () => {
            window.clearTimeout(resizeTimer);
            resizeTimer = window.setTimeout(initializeInfiniteSlider, 120);
        });
    }

    const pricingForm = document.getElementById('calculadoraPlanos');
    const integratedCount = document.getElementById('quantidadeIntegrados');
    const selectedPlanLabel = document.getElementById('planoSelecionado');
    const pricingResult = document.getElementById('resultadoPreco');
    const planOptions = document.querySelectorAll('input[name="plano"]');
    let selectedPlan = { name: 'Trimestral', months: 3, discount: 0 };

    const updateSelectedPlan = () => {
        if (selectedPlanLabel) {
            const discountText = selectedPlan.discount ? `, ${selectedPlan.discount}% de desconto` : '';
            selectedPlanLabel.textContent = `Plano selecionado: ${selectedPlan.name} (${selectedPlan.months} meses${discountText})`;
        }

        planOptions.forEach((option) => {
            option.checked = option.value === selectedPlan.name;
        });

    };

    const updatePricingResult = () => {
        if (!pricingResult || !integratedCount) {
            return false;
        }

        const enteredCount = integratedCount.value.trim();
        const count = Number(integratedCount.value);
        if (enteredCount === '' || !Number.isInteger(count) || count < 0) {
            pricingResult.textContent = 'Informe um número inteiro igual ou maior que zero.';
            return false;
        }

        const monthly = 3500 + (count * 5);
        const beforeDiscount = monthly * selectedPlan.months;
        const total = beforeDiscount * (1 - selectedPlan.discount / 100);
        const money = new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' });
        pricingResult.innerHTML = `Estimativa para <strong>${selectedPlan.name.toLowerCase()}</strong>: <strong>${money.format(total)}</strong> no período, equivalente a ${money.format(total / selectedPlan.months)} por mês. Inclui ${money.format(monthly)} mensais antes do desconto.`;
        return true;
    };

    planOptions.forEach((option) => {
        option.addEventListener('change', () => {
            if (!option.checked) {
                return;
            }

            selectedPlan = {
                name: option.value,
                months: Number(option.dataset.meses),
                discount: Number(option.dataset.desconto)
            };
            updateSelectedPlan();
            updatePricingResult();
        });
    });

    planOptions.forEach((option) => {
        if (option.checked) {
            selectedPlan = {
                name: option.value,
                months: Number(option.dataset.meses),
                discount: Number(option.dataset.desconto)
            };
        }
    });
    updateSelectedPlan();

    if (pricingForm) {
        pricingForm.addEventListener('submit', (event) => {
            event.preventDefault();
            updatePricingResult();
        });
    }

    integratedCount?.addEventListener('input', () => {
        if (pricingResult?.textContent.startsWith('Estimativa para')) {
            updatePricingResult();
        }
    });

    const automaticStepButtons = Array.from(document.querySelectorAll('.passo-botao'));
    const automaticProfileButtons = Array.from(document.querySelectorAll('.perfil-opcao'));
    if (automaticStepButtons.length > 1) {
        let automaticStepTimer;
        const scheduleAutomaticStep = () => {
            window.clearTimeout(automaticStepTimer);
            if (document.hidden) {
                return;
            }

            automaticStepTimer = window.setTimeout(() => {
                const activeIndex = automaticStepButtons.findIndex(
                    (button) => button.getAttribute('aria-pressed') === 'true'
                );
                const nextIndex = (activeIndex + 1) % automaticStepButtons.length;
                automaticStepButtons[nextIndex].click();
                scheduleAutomaticStep();
            }, 5000);
        };

        [...automaticStepButtons, ...automaticProfileButtons].forEach((button) => {
            button.addEventListener('click', scheduleAutomaticStep);
        });
        document.addEventListener('visibilitychange', scheduleAutomaticStep);
        scheduleAutomaticStep();
    }
    const currentYear = document.getElementById('anoAtual');
    if (currentYear) {
        currentYear.textContent = String(new Date().getFullYear());
    }
});

