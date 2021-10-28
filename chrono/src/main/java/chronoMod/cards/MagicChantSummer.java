package chronoMod.cards;

import chronoMod.ChronoMod;
import chronoMod.powers.MagicChantPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chronoMod.ChronoMod.makeCardPath;

public class MagicChantSummer extends AbstractDynamicCard {
    private static final String CARD_ID = MagicChantSummer.class.getSimpleName();
    public static final String ID = ChronoMod.makeID(CARD_ID);
    public static final String IMG = makeCardPath("skill/" + CARD_ID + ".png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION[0];

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = -2;
    private static final int BLOCK = 8;
    private static final int UPGRADE_PLUS_BLOCK = 4;

    public MagicChantSummer() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = BLOCK;
        this.cardsToPreview = new MagicChantFall();
        this.isEthereal = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        this.cantUseMessage = EXTENDED_DESCRIPTION;
        return false;
    }

    @Override
    public void triggerWhenDrawn() {
        this.applyPowers();
        AbstractPlayer p = AbstractDungeon.player;
        this.addToBot(new GainBlockAction(p, this.block));
        this.addToBot(new ApplyPowerAction(p, p, new MagicChantPower(p, this.cardsToPreview)));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            this.cardsToPreview.upgrade();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new MagicChantSummer();
    }
}
