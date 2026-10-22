document.addEventListener('DOMContentLoaded', () => {
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
    const funcionamentoArte = document.getElementById('funcionamentoRecorte');
    const funcionamentoAssetBase = funcionamentoVisual?.dataset.assetsBase || '../assets/';
    const funcionamentoViews = {
        empresa: [
            { src: 'imagem-1-empresa-passo-a-passo.svg', alt: 'Tablet com painel da empresa e indicadores de produção', offsetX: '-24px', offsetY: '-9px' },
            { src: 'imagem-2-empresa-passo-a-passo.svg', alt: 'Dois tablets sobrepostos com o painel de ranking da empresa', offsetX: '-40px', scale: 1.323 },
            { src: 'imagem-3-empresa-passo-a-passo.svg', alt: 'Tablet com metas e resultados de produção', offsetX: 'calc(clamp(0px, 1.5vw, 24px) - 34px)', offsetY: 'calc(clamp(0px, 1vw, 16px) + 31px)', scale: 1.1 }
        ],
        integrado: [
            { src: 'imagem-1-integrado-passo-a-passo.png', alt: 'Celular com formulário de cadastro do integrado', offsetX: '-15px', offsetY: '-53px', scale: 0.9261 },
            { src: 'imagem-2-integrado-passo-a-passo.png', alt: 'Dois celulares com gráficos de consumo e produção', offsetX: '-42px', offsetY: '10px', scale: 1.2 },
            { src: 'imagem-3-integrado-passo-a-passo.svg', alt: 'Celular com painel de ranking e a classificação de Marcela', width: 396, offsetX: '10px', offsetY: '-25px', scale: 0.83391 }
        ]
    };
    let selectedProfile = 'empresa';
    let selectedStep = 0;
    let imageTransitionTimer;

    const applyFunctioningView = (view) => {
        if (!funcionamentoImage || !funcionamentoArte) {
            return;
        }

        funcionamentoImage.src = `${funcionamentoAssetBase}${view.src}`;
        funcionamentoImage.alt = view.alt;
        funcionamentoArte.style.setProperty('--imagem-largura', view.width ? `${view.width}px` : '100%');
        funcionamentoArte.style.setProperty('--imagem-deslocamento-x', view.offsetX || '0px');
        funcionamentoArte.style.setProperty('--imagem-deslocamento-y', view.offsetY || '0px');
        funcionamentoArte.style.setProperty('--imagem-escala', view.scale || 1);
    };

    const showFunctioningView = () => {
        const view = funcionamentoViews[selectedProfile]?.[selectedStep];
        if (!view || !funcionamentoImage || !funcionamentoVisual) {
            return;
        }

        window.clearTimeout(imageTransitionTimer);
        funcionamentoVisual.classList.add('trocando-imagem');
        imageTransitionTimer = window.setTimeout(() => {
            applyFunctioningView(view);
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

        document.getElementById('funcionamento')?.setAttribute('data-perfil', profile);

        stepButtons.forEach((button, index) => {
            const selected = index === step;
            const item = button.closest('[data-passo]');
            item?.classList.toggle('passo-destaque', selected);
            item?.classList.toggle('passo-concluido', index < step);
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

    if (funcionamentoImage && funcionamentoVisual && funcionamentoArte) {
        applyFunctioningView(funcionamentoViews.empresa[0]);
    }

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

    const currentYear = document.getElementById('anoAtual');
    if (currentYear) {
        currentYear.textContent = String(new Date().getFullYear());
    }
});
