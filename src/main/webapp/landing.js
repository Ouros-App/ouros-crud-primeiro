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

    document.querySelectorAll('.faq-pergunta').forEach((button) => {
        button.addEventListener('click', () => {
            const isOpen = button.getAttribute('aria-expanded') === 'true';
            const answer = document.getElementById(button.getAttribute('aria-controls'));
            button.setAttribute('aria-expanded', String(!isOpen));
            if (answer) {
                answer.hidden = isOpen;
            }
        });
    });

    const profileButtons = document.querySelectorAll('.perfil-opcao');
    const profileFields = document.querySelectorAll('.funcionamento-passos [data-empresa][data-integrado]');
    const stepButtons = document.querySelectorAll('.passo-botao');
    const funcionamentoImage = document.getElementById('funcionamentoImagem');
    const funcionamentoVisual = document.getElementById('funcionamentoVisual');
    const funcionamentoViews = {
        empresa: [
            { src: 'assets/funcionamento-celular.svg', alt: 'Tela provisória 1 do funcionamento para empresas' },
            { src: 'assets/funcionamento-empresa-2.svg', alt: 'Tela provisória 2 do funcionamento para empresas' },
            { src: 'assets/funcionamento-empresa-3.svg', alt: 'Tela provisória 3 do funcionamento para empresas' }
        ],
        integrado: [
            { src: 'assets/funcionamento-integrado-1.svg', alt: 'Tela provisória 1 do funcionamento para integrados' },
            { src: 'assets/funcionamento-integrado-2.svg', alt: 'Tela provisória 2 do funcionamento para integrados' },
            { src: 'assets/funcionamento-integrado-3.svg', alt: 'Tela provisória 3 do funcionamento para integrados' }
        ]
    };
    let selectedProfile = 'empresa';
    let selectedStep = 0;
    let imageTransitionTimer;

    const showFunctioningView = () => {
        const view = funcionamentoViews[selectedProfile]?.[selectedStep];
        if (!view || !funcionamentoImage || !funcionamentoVisual) {
            return;
        }

        window.clearTimeout(imageTransitionTimer);
        funcionamentoVisual.classList.add('trocando-imagem');
        imageTransitionTimer = window.setTimeout(() => {
            funcionamentoImage.src = view.src;
            funcionamentoImage.alt = view.alt;
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

        featureSlider.addEventListener('scroll', () => {
            if (!cycleWidth || dragging) {
                return;
            }
            normalizeInfiniteSlider();
        }, { passive: true });

        featureSlider.addEventListener('pointerdown', (event) => {
            if (event.pointerType !== 'mouse' || event.button !== 0) {
                return;
            }

            dragging = true;
            pointerStart = event.clientX;
            scrollStart = featureSlider.scrollLeft;
            featureSlider.classList.add('arrastando');
            featureSlider.setPointerCapture(event.pointerId);
        });

        featureSlider.addEventListener('pointermove', (event) => {
            if (dragging) {
                featureSlider.scrollLeft = scrollStart - (event.clientX - pointerStart);
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
            featureSlider.scrollBy({ left: event.key === 'ArrowRight' ? distance : -distance, behavior: 'smooth' });
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
            featureSlider.scrollBy({ left: distance * direction, behavior: 'smooth' });
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
    const planButtons = document.querySelectorAll('.plano-agendar');
    let selectedPlan = { name: 'Trimestral', months: 3, discount: 0 };

    const updatePricingResult = () => {
        if (!pricingResult || !integratedCount) {
            return;
        }

        const count = Number(integratedCount.value);
        if (!Number.isInteger(count) || count < 0) {
            pricingResult.textContent = 'Informe um número inteiro igual ou maior que zero.';
            return;
        }

        const monthly = 3500 + (count * 5);
        const beforeDiscount = monthly * selectedPlan.months;
        const total = beforeDiscount * (1 - selectedPlan.discount / 100);
        const money = new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' });
        pricingResult.innerHTML = `Estimativa para <strong>${selectedPlan.name.toLowerCase()}</strong>: <strong>${money.format(total)}</strong> no período, equivalente a ${money.format(total / selectedPlan.months)} por mês. Inclui ${money.format(monthly)} mensais antes do desconto.`;
    };

    planButtons.forEach((button) => {
        button.addEventListener('click', () => {
            selectedPlan = {
                name: button.dataset.plano,
                months: Number(button.dataset.meses),
                discount: Number(button.dataset.desconto)
            };

            planButtons.forEach((planButton) => {
                const selected = planButton === button;
                planButton.setAttribute('aria-pressed', String(selected));
                planButton.closest('.plano-card')?.classList.toggle('plano-selecionado', selected);
            });

            if (selectedPlanLabel) {
                selectedPlanLabel.textContent = `Plano selecionado: ${selectedPlan.name} (${selectedPlan.months} meses${selectedPlan.discount ? `, ${selectedPlan.discount}% de desconto` : ''})`;
            }

            if (pricingResult) {
                pricingResult.textContent = 'Informe a quantidade de integrados e calcule o investimento estimado.';
            }

            document.getElementById('calculadoraPlanos')?.scrollIntoView({ behavior: 'smooth', block: 'center' });
            integratedCount?.focus({ preventScroll: true });
        });
    });

    if (pricingForm) {
        pricingForm.addEventListener('submit', (event) => {
            event.preventDefault();
            updatePricingResult();
        });
    }

    const currentYear = document.getElementById('anoAtual');
    if (currentYear) {
        currentYear.textContent = String(new Date().getFullYear());
    }
});
