package chronoMod.cards;

import chronoMod.ChronoMod;
import chronoMod.actions.GainJadeAction;
import chronoMod.actions.PrecognitionAction;
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

    public Precognition() {
        super(ID, IMG, TYPE, COLOR, RARITY, TARGET);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int effect = this.getEffectNum(p);
        if (effect > 0) {
            this.addToBot(new PrecognitionAction(p, this.upgraded, effect));
        }
        this.addToBot(new GainJadeAction(2));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        } else {
            if (EnergyPanel.totalCount < 2) {
                canUse = false;
                this.cantUseMessage = EXTENDED_DESCRIPTION;
            }
            return canUse;
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        if (EnergyPanel.totalCount >= 2) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Precognition();
    }
}
