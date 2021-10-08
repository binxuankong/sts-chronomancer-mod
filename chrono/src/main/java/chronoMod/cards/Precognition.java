package chronoMod.cards;

import chronoMod.ChronoMod;
import chronoMod.actions.ConsumeJadeAction;
import chronoMod.characters.Chronomancer;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static chronoMod.ChronoMod.makeCardPath;

public class Precognition extends AbstractXCostCard {
    public static final String ID = ChronoMod.makeID(Precognition.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION[0];

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Chronomancer.Enums.COLOR_BLUE;

    private static final int ENERGY_REQUIRED = 2;
    private static final int UPGRADE_PLUS_ENERGy = -1;

    public Precognition() {
        super(ID, IMG, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = ENERGY_REQUIRED;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int effect = this.getEffectNum(p);
        if (!this.upgraded) {
            effect--;
        }
        if (effect > 0) {
            for (int i = 0; i < effect; i++) {
                this.addToBot(new ConsumeJadeAction(p, 1));
            }
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        } else {
            int energy = EnergyPanel.totalCount;
            if (energy < this.magicNumber) {
                canUse = false;
                this.cantUseMessage = EXTENDED_DESCRIPTION;
            }
            return canUse;
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        int energy = EnergyPanel.totalCount;
        if (energy >= this.magicNumber) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_ENERGy);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Precognition();
    }
}
